package com.cn.sce.entity;


public class Sender {
    private Sender() {
    }

    public static void messageToFile(MessageEntity me) {
        Broker.put(me);
    }

    static {
        Receiver.consumeMessage();
    }
}
