package com.cn.sce.aop;

import com.cn.sce.exception.AuthorizationException;
import com.cn.sce.license.LicenseManager;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AspectConfigure {
    public static final String ASPECT_RULE = "execution(* *(..)) && @annotation(anno)";
    private static final Logger logger = LoggerFactory.getLogger(AspectConfigure.class);

    public AspectConfigure() {
    }

    protected AuthAndStatisticsAdvisorHandle getHandle() {
        return AuthAndStatisticsAdvisorHandleFactory.getInstance();
    }

    protected void before(JoinPoint joinPoint, BeanExposeMethodAble beanExposeMethodAble) throws AuthorizationException {
        this.getHandle().before(joinPoint, new ExposeMethodInfo(beanExposeMethodAble));
    }

    protected void after(JoinPoint joinPoint, BeanExposeMethodAble beanExposeMethodAble) throws StatisticException {
        if (LicenseManager.isValidated()) {
            this.getHandle().after(joinPoint, new ExposeMethodInfo(beanExposeMethodAble));
        }

    }

    protected void before(JoinPoint joinPoint, ExposeMethodAble exposeMethodAble) throws AuthorizationException {
        this.getHandle().before(joinPoint, new ExposeMethodInfo(exposeMethodAble));
    }

    protected void after(JoinPoint joinPoint, ExposeMethodAble exposeMethodAble) throws StatisticException {
        if (LicenseManager.isValidated()) {
            this.getHandle().after(joinPoint, new ExposeMethodInfo(exposeMethodAble));
        }

    }
}