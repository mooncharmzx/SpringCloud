package com.cn.sce.entity;

public class LogOut {
    private LogOut() {
    }

    public static void writeToFile(MessageEntity me) {
        Sender.messageToFile(me);
    }
}
