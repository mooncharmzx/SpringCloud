package com.cn.sce.enums;

import com.cn.sce.license.LicenseManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class ConstantConfig {

    public static final String FOLDER_PATH = ConstantConfig.class.getResource("/").getPath();
    public static final String BIYI_COMP_LOG_PATH = "BIYI_COMP_LOG_PATH";
    public static final String BIYI_COMP_CERT_PATH = "BIYI_COMP_CERT_PATH";
    private static final String WINDOWS = "windows";
    private static final Logger log = LoggerFactory.getLogger(ConstantConfig.class);
    private static String cacheLogPath = null;
    private static final String CTSI_YAML_LOG_PAHT = "sce";
    private static final String COMPONENT_STATICS_YAML_LOG_PATH = "component-statics";
    private static final String LOG_PATH_STATICS_YAML_LOG_PATH = "log-path";
    private static final String DEFAULLT_YAML_PATH = "config/application.yml";

    private ConstantConfig() {
    }

    public static String getLogPath() {
        if (StringUtils.isNotBlank(cacheLogPath)) {
            return cacheLogPath;
        } else {
            cacheLogPath = loadPath();
            return cacheLogPath;
        }
    }

    private static String loadPath() {
        String jsonLogPath = System.getProperty("BIYI_COMP_LOG_PATH");
        String projectCode = LicenseManager.getLicenseInfo().getProjectCode();
        if (StringUtils.isNotEmpty(jsonLogPath)) {
            cacheLogPath = jsonLogPath;
        } else {
            String pathYaml = null;

            try {
                pathYaml = getLogPathFromYaml("sce", "component-statics", "log-path");
            } catch (Exception var4) {
            }

            if (StringUtils.isNotBlank(pathYaml)) {
                cacheLogPath = pathYaml;
            } else if (isWindows()) {
                cacheLogPath = System.getProperty("user.dir").toLowerCase().trim() + "/logs";
            } else {
                cacheLogPath = System.getProperty("user.dir").toLowerCase().trim() + "/logs";
            }
        }

        if (StringUtils.isNotBlank(projectCode)) {
            cacheLogPath = cacheLogPath + File.separator + projectCode;
        }

        File logDIr = new File(cacheLogPath);
        if (!logDIr.exists()) {
            logDIr.mkdirs();
        }

        return cacheLogPath;
    }

    public static String getLogPathFromYaml(String ctsi, String component, String log) {
        Yaml yaml = new Yaml();
        String enviroment = getEnviroment("config/application.yml", yaml);
        if (StringUtils.isNotBlank(enviroment)) {
            String profilePath = "config/application.yml".replace("application", "application-" + enviroment);
            return getLogPath(profilePath, yaml, ctsi, component, log);
        } else {
            return "";
        }
    }

    private static String getLogPath(String profilePath, Yaml yaml, String ctsi, String component, String logPath) {
        try {
            InputStream inputStream = ConstantConfig.class.getClassLoader().getResourceAsStream(profilePath);
            Throwable var6 = null;

            String var12;
            try {
                HashMap<String, Object> map = (HashMap)yaml.load(inputStream);
                if (!map.containsKey(ctsi)) {
                    return "";
                }

                Object ctsiValue = map.get(ctsi);
                if (!(ctsiValue instanceof LinkedHashMap)) {
                    return "";
                }

                Map<String, Object> ctsiValueMap = (Map)ctsiValue;
                if (!ctsiValueMap.containsKey(component)) {
                    return "";
                }

                Object componentValue = ctsiValueMap.get(component);
                if (!(componentValue instanceof LinkedHashMap)) {
                    return "";
                }

                Map<String, Object> componentValueMap = (Map)componentValue;
                if (!componentValueMap.containsKey(logPath)) {
                    return "";
                }

                var12 = (String)componentValueMap.get(logPath);
            } catch (Throwable var23) {
                var6 = var23;
                throw var23;
            } finally {
                if (inputStream != null) {
                    if (var6 != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable var22) {
                            var6.addSuppressed(var22);
                        }
                    } else {
                        inputStream.close();
                    }
                }

            }

            return var12;
        } catch (IOException var25) {
            log.error(Arrays.toString(var25.getStackTrace()));
            return "";
        }
    }

    private static String getEnviroment(String path, Yaml yaml) {
        try {
            InputStream inputStream = ConstantConfig.class.getClassLoader().getResourceAsStream(path);
            Throwable var3 = null;

            String var8;
            try {
                Map<String, Object> applicationYaml = (Map)yaml.load(inputStream);
                if (!applicationYaml.containsKey("spring")) {
                    return "";
                }

                HashMap<String, Object> springMap = (HashMap)applicationYaml.get("spring");
                if (!springMap.containsKey("profiles")) {
                    return "";
                }

                HashMap<String, Object> profilesMap = (HashMap)springMap.get("profiles");
                if (!profilesMap.containsKey("active")) {
                    return "";
                }

                String active = (String)profilesMap.get("active");
                if (active.contains(",")) {
                    String[] strings = active.split(",");
                    if (strings.length <= 0) {
                        return "";
                    }

                    String var9 = strings[0];
                    return var9;
                }

                var8 = active;
            } catch (Throwable var21) {
                var3 = var21;
                throw var21;
            } finally {
                if (inputStream != null) {
                    if (var3 != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable var20) {
                            var3.addSuppressed(var20);
                        }
                    } else {
                        inputStream.close();
                    }
                }

            }

            return var8;
        } catch (IOException var23) {
            log.error(Arrays.toString(var23.getStackTrace()));
            return "";
        }
    }

    public static Boolean isWindows() {
        String osName = System.getProperty("os.name").toLowerCase();
        return osName.contains("windows") ? Boolean.TRUE : Boolean.FALSE;
    }

}
