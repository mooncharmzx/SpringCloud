package com.cn.sce.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Broker {
    private static final Logger logger = LoggerFactory.getLogger(Broker.class);
    public static final int QUEUE_COUNT = 60000;
    protected static final BlockingQueue<MessageEntity> MQCACHE = new ArrayBlockingQueue(60000);

    private Broker() {
    }

    public static void put(MessageEntity msg) {
        try {
            MQCACHE.put(msg);
        } catch (Exception var2) {
            logger.error("put log error：{}", var2.getMessage());
        }

    }

    public static MessageEntity take() {
        try {
            return (MessageEntity)MQCACHE.take();
        } catch (Exception var1) {
            logger.error("take log error：{}", var1.getMessage());
            return null;
        }
    }

    public static int count() {
        return MQCACHE.size();
    }
}
