package com.cn.sce.aop;

import com.cn.sce.exception.AuthorizationException;
import org.aspectj.lang.JoinPoint;

public interface AuthAndStatisticsAdvisorHandle {

    void before(JoinPoint var1, ExposeMethodInfo var2) throws AuthorizationException;

    void after(JoinPoint var1, ExposeMethodInfo var2) throws StatisticException;

}
