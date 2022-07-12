//package com.cn.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
//import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
//import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
//import org.springframework.stereotype.Service;
//
//import java.net.Inet4Address;
//
//@Service
//public class ConsulRegistry extends ConsulServiceRegistry {
//
//    private ConsulDiscoveryProperties properties;
//
//    private ConsulClient consulClient;
//
//    @Value("${spring.application.name}")
//    private String applicationName;
//
//    public ConsulRegistry(ConsulClient client, ConsulDiscoveryProperties properties, @Autowired(required = false) TtlScheduler ttlScheduler, HeartbeatProperties heartbeatProperties) {
//        super(client, properties, ttlScheduler, heartbeatProperties);
//        this.properties = properties;
//        this.consulClient = client;
//    }
//
//    @SneakyThrows
//    @Override
//    public void register(ConsulRegistration reg) {
//        //获取服务器的ip
//        String address = Inet4Address.getLocalHost().getHostAddress();
//        NewService newService = reg.getService();
//        newService.setAddress(address);
//        newService.getCheck().setHttp("http://" + address + ":" + reg.getService().getPort() + properties.getHealthCheckPath());
//        newService.setId(reg.getService().getName() + "-" + address + ":" + reg.getService().getPort() + "-" + UUID.randomUUID().toString().replace("-",""));
//        super.register(reg);
//    }
//
//    @Override
//    public void deregister(ConsulRegistration reg) {
//        super.deregister(reg);
//
//        //查询no pass的服务实例
//        HealthServicesRequest request = HealthServicesRequest.newBuilder().setPassing(false).build();
//        List<HealthService> services = consulClient.getHealthServices(applicationName, request).getValue();
//
//        for (HealthService service : services) {
//            for (Check check : service.getChecks()) {
//                //注销无效实例
//                if (check.getStatus() != Check.CheckStatus.PASSING) {
//                    consulClient.agentServiceDeregister(check.getServiceId());
//                }
//            }
//        }
//    }
//
//}
