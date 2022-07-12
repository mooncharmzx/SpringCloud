package com.cn.config.redislock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.admin.Result;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import javax.servlet.http.HttpServletResponseWrapper;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Aspect
@Configuration
@ConditionalOnClass(DistributedLock.class)
@AutoConfigureAfter(DistributedLockAutoConfiguration.class)
public class DistributedLockAspectConfiguration {

    private final Logger logger = LoggerFactory.getLogger(DistributedLockAspectConfiguration.class);

    @Autowired
    private DistributedLock distributedLock;

    @Pointcut("@annotation(RedisLock)")
    private void lockPoint(){

    }

    @Around("lockPoint()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        RedisLock redisLock = method.getAnnotation(RedisLock.class);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        Result result = new Result();
        String params = (String) methodBefore(pjp, uuid).get("params");//redisKey
        JSONObject jsonObject = JSON.parseObject(params);
        String key = jsonObject.getString("redisKey");
        Object[] args = pjp.getArgs();

        if(StringUtils.isEmpty(key)){
            key = Arrays.toString(args);
        }

        int retryTimes = redisLock.action().equals(RedisLock.LockFailAction.CONTINUE) ? redisLock.retryTimes() : 0;
        boolean lock = distributedLock.lock(key, redisLock.keepMills(), retryTimes, redisLock.sleepMills(),redisLock.index());
        if(!lock) {
            logger.info("get lock failed : " + key);
            result.setCode(-999);
            result.setMsg("请求超时!操作过快,请稍等!");
            return result;
        }

        //得到锁,执行方法，释放锁
        logger.debug("get lock success : " + key);
        try {
            Object object = pjp.proceed(args);
            if (object instanceof Result) {
                result = (Result) object;
                return result;
            } else {
                return object;
            }
        } catch (Exception e) {
            logger.error("execute locked method occured an exception", e);
        } finally {
            boolean releaseResult = distributedLock.releaseLock(key);
            logger.debug("release lock : " + key + (releaseResult ? " success" : " failed"));
        }
        return null;
    }

    public Map<String, Object> methodBefore(JoinPoint joinPoint, String uuid) {
        // 打印请求内容
        Map<String, Object> paramMap = new HashMap<>();
        try {
            // 下面两个数组中，参数值和参数名的个数和位置是一一对应的。
            Object[] objs = joinPoint.getArgs();
            String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames(); // 参数名

            for (int i = 0; i < objs.length; i++) {
                if (!(objs[i] instanceof ExtendedServletRequestDataBinder) && !(objs[i] instanceof HttpServletResponseWrapper)) {
                    paramMap.put(argNames[i], objs[i]);
                }
            }
            if (paramMap.size() > 0) {
                logger.info("\n[{}]方法:{}\n参数:{}", uuid, joinPoint.getSignature(), JSONObject.toJSONString(paramMap));
            }
        } catch (Exception e) {
            logger.error("[{}]AOP methodBefore:", uuid, e);
        }

        return paramMap;
    }
}
