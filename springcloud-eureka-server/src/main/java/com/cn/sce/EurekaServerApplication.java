package com.cn.sce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //当前使用eureka的server
public class EurekaServerApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {

        SpringApplication.run(EurekaServerApplication.class,args);
    }

    @Override
    protected SpringApplicationBuilder  configure(SpringApplicationBuilder builder) {
        return builder.sources(EurekaServerApplication.class);
    }
}
