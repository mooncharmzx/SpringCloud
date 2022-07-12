package com.cn.admin;
import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;
}
