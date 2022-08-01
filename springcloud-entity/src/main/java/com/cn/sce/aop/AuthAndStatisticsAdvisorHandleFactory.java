package com.cn.sce.aop;

public final class AuthAndStatisticsAdvisorHandleFactory {
    private static AuthAndStatisticsAdvisorHandle advisorHandle = null;

    AuthAndStatisticsAdvisorHandleFactory() {
    }

    public static final AuthAndStatisticsAdvisorHandle getInstance() {
        if (advisorHandle == null) {
            advisorHandle = new JsonLogsAuthAndStatisticsAdvisorHandleImpl();
        }

        return advisorHandle;
    }
}
