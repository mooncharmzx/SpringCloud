package com.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.bigdata.*"})
public class SpringCloudBigDataApplication {

    public static void main( String[] args ){
        SpringApplication.run(SpringCloudBigDataApplication.class, args);
    }
}
