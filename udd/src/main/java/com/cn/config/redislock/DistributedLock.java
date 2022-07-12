package com.cn.config.redislock;

public interface DistributedLock {

    long TIMEOUT_MILLIS = 30000;

    int RETRY_TIMES = Integer.MAX_VALUE;

    long SLEEP_MILLIS = 500;

    boolean lock(String key);

    boolean lock(String key, int retryTimes);

    boolean lock(String key, int retryTimes, long sleepMillis);

    boolean lock(String key, long expire);

    boolean lock(String key, long expire, int retryTimes);

    boolean lock(String key, long expire, int retryTimes, long sleepMillis);

    boolean lock(String key, long expire, int retryTimes, long sleepMillis, String requestId);

    /**
     *
     * @param key redis key
     * @param expire 过期时间
     * @param retryTimes 重试次数
     * @param sleepMillis 重试间隔时间
     * @param index redis 数据库索引
     * @return
     */
    boolean lock(String key, long expire, int retryTimes, long sleepMillis, int index);

    /**
     *
     * @param key redis key
     * @return
     */
    boolean releaseLock(String key);
}
