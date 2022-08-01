package com.cn.sce.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);
    public static final boolean ISSTART = true;

    private Receiver() {
    }

    public static void consumeMessage() {
        Runnable consumer = new Runnable() {
            public void run() {
                try {
                    MessageEntity el = null;

                    while(true) {
                        while(true) {
                            el = Broker.take();
                            if (el != null) {
                                LogFile.writeFile(el);
                            } else {
                                Thread.sleep(1000L);
                            }
                        }
                    }
                } catch (Exception var2) {
                    Receiver.logger.error("take log and write thread error：{}", var2);
                }
            }
        };
        Thread thread = new Thread(consumer);
        thread.start();
    }
}
