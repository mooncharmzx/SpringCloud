package com.cn.sce.aop;

import java.io.Serializable;

public class ExposeMethodInfo implements Serializable {

    private String component;
    private String method;

    public ExposeMethodInfo(BeanExposeMethodAble beanExposeMethodAble) {
        this.component = beanExposeMethodAble.component();
        this.method = beanExposeMethodAble.method();
    }

    public ExposeMethodInfo(ExposeMethodAble exposeMethodAble) {
        this.component = exposeMethodAble.component();
        this.method = exposeMethodAble.method();
    }

    public String getComponent() {
        return this.component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
