package com.cn.sce.model;

public interface SceDefaults {
    public interface Security {
        public interface RememberMe {
            String key = null;
        }

        public interface Authentication {
            public interface Jwt {
                String secret = null;
                long tokenValidityInSeconds = 1800L;
                long tokenValidityInSecondsForRememberMe = 2592000L;
            }
        }

        public interface ClientAuthorization {
            String accessTokenUri = null;
            String tokenServiceId = null;
            String clientId = null;
            String clientSecret = null;
        }
    }

    public interface Metrics {
        public interface Logs {
            boolean enabled = false;
            long reportFrequency = 60L;
        }

        public interface Jmx {
            boolean enabled = true;
        }
    }

    public interface Swagger {
        String title = "Ctsi Application API";
        String description = "Ctsi API documentation";
        String version = "1.0.0";
        String termsOfServiceUrl = null;
        String contactName = null;
        String contactUrl = null;
        String contactEmail = null;
        String license = null;
        String licenseUrl = null;
        String defaultIncludePattern = "/api/.*";
        String host = null;
        String[] protocols = new String[0];
    }

    public interface Async {
        int corePoolSize = 2;
        int maxPoolSize = 50;
        int queueCapacity = 10000;
    }
}
