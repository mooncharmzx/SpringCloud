package com.rema.util;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.agent.model.NewService;
import com.google.common.base.Joiner;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.discovery.HeartbeatProperties;
import org.springframework.cloud.consul.discovery.TtlScheduler;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;

public class ConsulServiceRegistryUtil extends ConsulServiceRegistry {

    /* 服务名称 */
    private String serviceName;

    /**
     * 自定义构造器
     * @param client
     * @param properties
     * @param ttlScheduler
     * @param heartbeatProperties
     * @param serviceName
     */
    public ConsulServiceRegistryUtil(ConsulClient client,
                                   ConsulDiscoveryProperties properties,
                                   TtlScheduler ttlScheduler,
                                   HeartbeatProperties heartbeatProperties,
                                   String serviceName) {

        super(client, properties, ttlScheduler, heartbeatProperties);

        this.serviceName = serviceName;
    }

    /**
     * 自定义注册服务实例  instanceID -> appName:addr:port
     * @param reg
     */
    @Override
    public void register(ConsulRegistration reg) {
        NewService service = reg.getService();

        // 设置服务名、实例ID
        service.setName(this.serviceName);
        service.setId(Joiner.on(":").join(this.serviceName, service.getAddress(), String.valueOf(service.getPort())));

        super.register(reg);
    }

}
