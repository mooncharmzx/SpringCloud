package com.cn.sce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //当前使用eureka的server
public class EurekaSafeServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(EurekaSafeServerApplication.class,args);
    }
}
