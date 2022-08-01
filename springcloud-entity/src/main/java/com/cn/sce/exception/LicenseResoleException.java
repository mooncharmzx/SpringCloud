package com.cn.sce.exception;

public class LicenseResoleException extends RuntimeException {
    public LicenseResoleException(String message) {
        super(message);
    }

    public LicenseResoleException(String message, Throwable cause) {
        super(message, cause);
    }
}