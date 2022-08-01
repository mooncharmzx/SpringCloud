package com.cn.sce.enmu;

public enum AppHttpCodeEnum {

    NEED_LOGIN(1,"登录失败!");

    private int ahCode;

    private String msg;

    AppHttpCodeEnum(int ahCode,String msg){
        this.ahCode = ahCode;
        this.msg = msg;
    }

    public int getAhCode() {
        return ahCode;
    }

    public String getMsg(){
        return msg;
    }
}
