package com.cn.sce.admin;
import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;
}
