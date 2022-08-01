package com.cn.sce.exception;

public class RestControllerException extends RuntimeException {

    public RestControllerException() {
        super();
    }

    public RestControllerException(String message) {
        super(message);
    }

    public RestControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestControllerException(Throwable cause) {
        super(cause);
    }

    public RestControllerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
