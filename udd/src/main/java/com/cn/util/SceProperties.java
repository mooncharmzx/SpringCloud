package com.cn.util;


import com.cn.sce.model.SceDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(
        prefix = "sce",
        ignoreUnknownFields = true
)
@EnableConfigurationProperties(SceProperties.class)
public class SceProperties {

    private final Async async = new Async();
    private final Swagger swagger = new Swagger();
    private final Metrics metrics = new Metrics();
    private final CorsConfiguration cors = new CorsConfiguration();
    private final Security security = new Security();
    private List<String> corspaths;

    public SceProperties() {
    }

    public List<String> getCorspaths() {
        return this.corspaths;
    }

    public void setCorspaths(List<String> corspaths) {
        this.corspaths = corspaths != null ? new ArrayList(corspaths) : null;
    }

    public Async getAsync() {
        return this.async;
    }

    public Swagger getSwagger() {
        return this.swagger;
    }

    public Metrics getMetrics() {
        return this.metrics;
    }

    public CorsConfiguration getCors() {
        return this.cors;
    }

    public Security getSecurity() {
        return this.security;
    }

    public static class Security {
        private final ClientAuthorization clientAuthorization = new ClientAuthorization();
        private final Authentication authentication = new Authentication();
        private final RememberMe rememberMe = new RememberMe();

        public Security() {
        }

        public ClientAuthorization getClientAuthorization() {
            return this.clientAuthorization;
        }

        public Authentication getAuthentication() {
            return this.authentication;
        }

        public RememberMe getRememberMe() {
            return this.rememberMe;
        }

        public static class RememberMe {
            private String key;

            public RememberMe() {
                this.key = SceDefaults.Security.RememberMe.key;
            }

            public String getKey() {
                return this.key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        public static class Authentication {
            private final Cas cas = new Cas();
            private final Jwt jwt = new Jwt();

            public Authentication() {
            }

            public Cas getCas() {
                return this.cas;
            }

            public Jwt getJwt() {
                return this.jwt;
            }

            public static class Jwt {
                private String secret;
                private long tokenValidityInSeconds;
                private long tokenValidityInSecondsForRememberMe;

                public Jwt() {
                    this.secret = SceDefaults.Security.Authentication.Jwt.secret;
                    this.tokenValidityInSeconds = 1800L;
                    this.tokenValidityInSecondsForRememberMe = 2592000L;
                }

                public String getSecret() {
                    return this.secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public long getTokenValidityInSeconds() {
                    return this.tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return this.tokenValidityInSecondsForRememberMe;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }

            public static class Cas {
                private String casServerUrlPrefix;

                public Cas() {
                }

                public String getCasServerUrlPrefix() {
                    return this.casServerUrlPrefix;
                }

                public void setCasServerUrlPrefix(String casServerUrlPrefix) {
                    this.casServerUrlPrefix = casServerUrlPrefix;
                }
            }
        }

        public static class ClientAuthorization {
            private String accessTokenUri;
            private String tokenServiceId;
            private String clientId;
            private String clientSecret;

            public ClientAuthorization() {

                this.accessTokenUri = SceDefaults.Security.ClientAuthorization.accessTokenUri;
                this.tokenServiceId = SceDefaults.Security.ClientAuthorization.tokenServiceId;
                this.clientId = SceDefaults.Security.ClientAuthorization.clientId;
                this.clientSecret = SceDefaults.Security.ClientAuthorization.clientSecret;
            }

            public String getAccessTokenUri() {
                return this.accessTokenUri;
            }

            public void setAccessTokenUri(String accessTokenUri) {
                this.accessTokenUri = accessTokenUri;
            }

            public String getTokenServiceId() {
                return this.tokenServiceId;
            }

            public void setTokenServiceId(String tokenServiceId) {
                this.tokenServiceId = tokenServiceId;
            }

            public String getClientId() {
                return this.clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getClientSecret() {
                return this.clientSecret;
            }

            public void setClientSecret(String clientSecret) {
                this.clientSecret = clientSecret;
            }
        }
    }

    public static class Metrics {
        private final Jmx jmx = new Jmx();
        private final Logs logs = new Logs();

        public Metrics() {
        }

        public Jmx getJmx() {
            return this.jmx;
        }

        public Logs getLogs() {
            return this.logs;
        }

        public static class Logs {
            private boolean enabled = false;
            private long reportFrequency = 60L;

            public Logs() {
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public long getReportFrequency() {
                return this.reportFrequency;
            }

            public void setReportFrequency(long reportFrequency) {
                this.reportFrequency = reportFrequency;
            }
        }

        public static class Jmx {
            private boolean enabled = true;

            public Jmx() {
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }
    }

    public static class Swagger {
        private String title = "sce Application API";
        private String description = "sce API documentation";
        private String version = "1.0.0";
        private String termsOfServiceUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;
        private String license;
        private String licenseUrl;
        private String defaultIncludePattern;
        private String host;
        private String[] protocols;

        public Swagger() {
            this.termsOfServiceUrl = SceDefaults.Swagger.termsOfServiceUrl;
            this.contactName = SceDefaults.Swagger.contactName;
            this.contactUrl = SceDefaults.Swagger.contactUrl;
            this.contactEmail = SceDefaults.Swagger.contactEmail;
            this.license = SceDefaults.Swagger.license;
            this.licenseUrl = SceDefaults.Swagger.licenseUrl;
            this.defaultIncludePattern = "/api/.*";
            this.host = SceDefaults.Swagger.host;
            this.protocols = SceDefaults.Swagger.protocols;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTermsOfServiceUrl() {
            return this.termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getContactName() {
            return this.contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactUrl() {
            return this.contactUrl;
        }

        public void setContactUrl(String contactUrl) {
            this.contactUrl = contactUrl;
        }

        public String getContactEmail() {
            return this.contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getLicense() {
            return this.license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return this.licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String getDefaultIncludePattern() {
            return this.defaultIncludePattern;
        }

        public void setDefaultIncludePattern(String defaultIncludePattern) {
            this.defaultIncludePattern = defaultIncludePattern;
        }

        public String getHost() {
            return this.host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String[] getProtocols() {
            return this.protocols;
        }

        public void setProtocols(String[] protocols) {
            this.protocols = protocols;
        }
    }

    public static class Async {
        private int corePoolSize = 2;
        private int maxPoolSize = 50;
        private int queueCapacity = 10000;

        public Async() {
        }

        public int getCorePoolSize() {
            return this.corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return this.maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return this.queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }
}
