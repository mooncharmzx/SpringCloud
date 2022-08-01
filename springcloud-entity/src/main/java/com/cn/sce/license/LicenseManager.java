package com.cn.sce.license;

import com.cn.sce.entity.LicenseInfo;
import com.cn.sce.enums.ConstantConfig;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class LicenseManager {
    private static final Logger log = LoggerFactory.getLogger(LicenseManager.class);
    public static final String EVM_LICENSE_PATH = "license.path";
    public static final String JSON_LOGS_EVM_LICENSE_PATH = "BIYI_COMP_CERT_PATH";
    public static final String FILE_SEPERATOR = "/";
    private static final ComponentLicense COMPONENT_LICENSE = new ComponentLicense();

    private LicenseManager() {
    }

    private static String parseCertPath(String certPathYaml) {
        if (certPathYaml.contains("classpath")) {
            String[] path = certPathYaml.split(":");
            String prefixPath = path[1];
            if (prefixPath.substring(0, 1).equals("/")) {
                prefixPath = prefixPath.substring(1);
            }

            return LicenseManager.class.getResource("/").getPath() + prefixPath;
        } else {
            return certPathYaml;
        }
    }

    public static ValidateResult validate(String component, JoinPoint joinPoint) {
        return COMPONENT_LICENSE.validate(component, joinPoint.getSignature().getDeclaringType().getName() + "." + joinPoint.getSignature().getName());
    }

    public static String getError() {
        return COMPONENT_LICENSE.getError();
    }

    public static LicenseInfo getLicenseInfo() {
        return isValidated() ? COMPONENT_LICENSE.getLicenseInfo() : null;
    }

    public static boolean isValidated() {
        return COMPONENT_LICENSE.isValidated();
    }

    public static byte[] getPubliceKey() {
        return COMPONENT_LICENSE.isValidated() ? COMPONENT_LICENSE.getPublickey() : new byte[0];
    }

    static {
        String jsonLogsPath = System.getProperty("BIYI_COMP_CERT_PATH");
        if (StringUtils.isNotEmpty(jsonLogsPath)) {
            COMPONENT_LICENSE.setLicensePath(jsonLogsPath);
            COMPONENT_LICENSE.resolve(COMPONENT_LICENSE.getLicensePath());
        } else {
            String certPathYaml = null;

            try {
                certPathYaml = ConstantConfig.getLogPathFromYaml("sce", "component-statics", "cert-path");
            } catch (Exception var4) {
            }

            if (StringUtils.isNotBlank(certPathYaml)) {
                if (certPathYaml.contains("classpath")) {
                    String[] path = certPathYaml.split(":");
                    String prefixPath = path[1];
                    if (prefixPath.substring(0, 1).equals("/")) {
                        prefixPath = prefixPath.substring(1);
                    }

                    COMPONENT_LICENSE.setLicensePath((String)null);
                    COMPONENT_LICENSE.resolve(prefixPath);
                    log.info("证书的路径是：" + prefixPath);
                } else {
                    COMPONENT_LICENSE.setLicensePath(certPathYaml);
                    COMPONENT_LICENSE.resolve((String)null);
                    log.info("证书的路径是：" + certPathYaml);
                }
            } else {
                COMPONENT_LICENSE.setLicensePath((String)null);
                COMPONENT_LICENSE.resolve("license.lc");
                log.info("证书的路径是：license.lc");
            }
        }

    }
}
