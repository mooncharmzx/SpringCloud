package com.cn.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends Exception {

    public CustomException(){}
    public CustomException(String msg, HttpStatus httpStatus){
        super(msg);
    }
}
