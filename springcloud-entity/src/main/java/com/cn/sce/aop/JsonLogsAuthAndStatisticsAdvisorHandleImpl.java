package com.cn.sce.aop;

import com.cn.sce.entity.ComponentInfo;
import com.cn.sce.entity.LogContentInfo;
import com.cn.sce.entity.LogOut;
import com.cn.sce.entity.MessageEntity;
import com.cn.sce.enums.ConstantConfig;
import com.cn.sce.exception.AuthorizationException;
import com.cn.sce.license.LicenseManager;
import com.cn.sce.license.ValidateResult;
import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogFile;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JsonLogsAuthAndStatisticsAdvisorHandleImpl implements AuthAndStatisticsAdvisorHandle {
    private static final Logger logger = LoggerFactory.getLogger(LogFile.class);
    private static String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss:SSSSSSSSSXXX";

    public JsonLogsAuthAndStatisticsAdvisorHandleImpl() {
    }

    public void before(JoinPoint joinPoint, ExposeMethodInfo exposeMethodInfo) throws AuthorizationException {
        ValidateResult validated = LicenseManager.validate(exposeMethodInfo.getComponent(), joinPoint);
        if (!validated.isResult()) {
            logger.error(validated.getError());
            throw new AuthorizationException(validated.getError());
        }
    }

    public void after(JoinPoint joinPoint, ExposeMethodInfo exposeMethodInfo) throws StatisticException {
        try {
            ComponentInfo cinfo = LicenseManager.getLicenseInfo().getComponentById(exposeMethodInfo.getComponent());
            LogContentInfo logContenInfo = new LogContentInfo((new SimpleDateFormat(DATE_FORMAT)).format(new Date()), LicenseManager.getLicenseInfo().getComCode(), LicenseManager.getLicenseInfo().getComName(), LicenseManager.getLicenseInfo().getProjectName(), LicenseManager.getLicenseInfo().getProjectCode(), exposeMethodInfo.getComponent(), exposeMethodInfo.getMethod(), cinfo.getCompType(), cinfo.getCompVersion(), cinfo.getCompLanguage(), LicenseManager.getLicenseInfo().getUser());
            logger.info("生成的logContentInfo是：" + logContenInfo);
            Gson gson = new Gson();
            String strLog = gson.toJson(logContenInfo);
            logger.info("日志的内容是：" + strLog);
            MessageEntity me = new MessageEntity();
            me.setPath(ConstantConfig.getLogPath());
            me.setConent(strLog);
            LogOut.writeToFile(me);
        } catch (Exception var8) {
            throw new StatisticException(var8.getMessage(), var8);
        }
    }
}