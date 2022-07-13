package com.rema;

import com.ecwid.consul.v1.ConsulClient;
import com.rema.util.ConsulServiceRegistryUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 *
 *
 *
 */
@SpringBootApplication
public class RegisterManualApplication {

    public static void main( String[] args ) {

        SpringApplication.run(RegisterManualApplication.class, args);
    }

    /**
     * 实例化自定义Consul服务注册
     * @param client
     * @param properties
     * @param ttlScheduler
     * @param heartbeatProperties
     * @param environment
     * @return
     */
    @Bean
    @Primary
    public ConsulServiceRegistry consulServiceRegistry(ConsulClient client,
                                                       ConsulDiscoveryProperties properties,
                                                       @Autowired(required = false) TtlScheduler ttlScheduler,
                                                       HeartbeatProperties heartbeatProperties,
                                                       ConfigurableEnvironment environment) {

        String serviceName = environment.getProperty("appName", StringUtils.EMPTY);

        return new ConsulServiceRegistryUtil(client, properties, ttlScheduler, heartbeatProperties, serviceName);
    }
}
