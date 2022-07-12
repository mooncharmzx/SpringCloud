package com.cn.config.redislock;

import lombok.Data;

@Data
public class LockContent {

    //开始时间
    long startTime;

    //
    Long lockExpire;

    //等待时间
    long expireTime;

    //请求id
    String requestId;

    //处理线程
    Thread thread;

    long bizExpire;

    //请求次数
    int lockCount;
}
