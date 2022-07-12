package com.cn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard  // 表示开启HystrixDashboard
@EnableCircuitBreaker //激活熔断器功能
public class HystrixDashBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(HystrixDashBoardApplication.class,args);
    }
}
