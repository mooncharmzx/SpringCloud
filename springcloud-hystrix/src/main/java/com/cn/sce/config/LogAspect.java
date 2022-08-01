package com.cn.sce.config;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Around(value = "@annotation(systemLog)")
    public Object checkLogin(ProceedingJoinPoint join, SystemLog systemLog) throws Throwable {

        // 获得被注解的方法的执行结果
        Object ret = join.proceed();
        System.out.println(JSONObject.toJSONString(ret));
        // 下面准备加是验证登录身份的逻辑代码
        return null;
    }

}
