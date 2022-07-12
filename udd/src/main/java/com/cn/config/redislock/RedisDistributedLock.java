package com.cn.config.redislock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisDistributedLock extends AbstractDistributedLock {

    private final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);

    @Autowired
    private final RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private final JedisConnectionFactory jedisConnectionFactory;

    private final ThreadLocal<String> lockFlag = new ThreadLocal<>();

    public static final String UNLOCK_LUA;

    /**
     * 每个key的过期时间 {@link LockContent}
     */
    private final Map<String, LockContent> lockContentMap = new ConcurrentHashMap<>(512);

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call(\"get\",KEYS[1]) == ARGV[1] ");
        sb.append("then ");
        sb.append("    return redis.call(\"del\",KEYS[1]) ");
        sb.append("else ");
        sb.append("    return 0 ");
        sb.append("end ");
        UNLOCK_LUA = sb.toString();
    }

    public RedisDistributedLock(RedisTemplate<Object, Object> redisTemplate, JedisConnectionFactory jedisConnectionFactory) {
        super();
        this.redisTemplate = redisTemplate;
        this.jedisConnectionFactory = jedisConnectionFactory;
    }

    @Override
    public boolean lock(String key, long expire, int retryTimes, long sleepMillis,int index) {
        jedisConnectionFactory.setDatabase(index);
        boolean result = setRedis(key, expire);
        logger.info(result ?"获取锁成功":"获取锁失败");
        // 如果获取锁失败，按照传入的重试次数进行重试
        logger.info("重试次数:"+retryTimes);
        while((!result) && retryTimes-- > 0){
            logger.info("循环次数"+retryTimes);
            try {
                logger.debug("lock failed, retrying..." + retryTimes);
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                return false;
            }
            result = setRedis(key, expire);
        }
        return result;
    }

    /**
     * 存储数据到缓存中，并制定过期时间以及当 Key 存在时是否覆盖
     *
     * @param key   缓存键
     *  value 缓存值
     *  nxxx  该的值只能取 NX 或者 XX，
     *              如果取 NX，则只有当 key 不存在时才进行 set
     *              如果取 XX，则只有当 key 已经存在时才进行 set
     *  expx  该的值只能取 EX 或者 PX，代表数据过期时间的单位，EX 代表秒，PX 代表毫秒
     *  time  过期时间，单位是 expx 所代表的单位
     * @return
     */
    private boolean setRedis(String key, long expire) {
        try {

            logger.info("设置redis缓存key："+key);
            String result = redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                JedisCommands commands = (JedisCommands) redisConnection.getNativeConnection();
                String uuid = UUID.randomUUID().toString();
                lockFlag.set(uuid);

                LockContent lockContent = new LockContent();
                lockContent.setRequestId(uuid);
                lockContent.setThread(Thread.currentThread());
//                lockContentMap.put(key,lockContent);
                return commands.set(key, uuid, "NX", "PX", expire);
            });

            logger.info("setredis缓存:"+result);
            return !StringUtils.isEmpty(result);

        } catch (Exception e) {
            logger.error("set redis occured an exception", e);
        }
        return false;
    }

    @Override
    public boolean releaseLock(String key) {

        // 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，所以不能直接删除
        try {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> args = new ArrayList<>();
            args.add(lockFlag.get());

            // 使用lua脚本删除redis中匹配value的key，可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
            // spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，所以只能拿到原redis的connection来执行脚本

            Long result = redisTemplate.execute((RedisCallback<Long>) redisConnection -> {
                Object nativeConnection = redisConnection.getNativeConnection();
                // 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                // 集群模式
                if (nativeConnection instanceof JedisCluster) {
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }

                // 单机模式
                else if (nativeConnection instanceof Jedis) {
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            });

            lockContentMap.remove(key);
            return result != null && result > 0;
        } catch (Exception e) {
            logger.error("release lock occured an exception", e);
        }
        return false;
    }
}
