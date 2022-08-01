package com.cn.sce.exception;

public class EncryptException extends RuntimeException {
    public EncryptException(Throwable cause) {
        super(cause);
    }

    public EncryptException(String message) {
        super(message);
    }

    public EncryptException(String message, Throwable cause) {
        super(message, cause);
    }
}