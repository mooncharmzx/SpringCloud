package com.rema.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ecwid.consul.v1.agent.model.NewService;
import com.rema.util.ConsulServiceRegistryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistration;
import org.springframework.cloud.consul.serviceregistry.ConsulServiceRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private ConsulServiceRegistry consulServiceRegistry;

    @RequestMapping("put")
    public String put(@RequestParam String params){

        JSONObject jsonObject = JSON.parseObject(params);

        InetUtilsProperties inetUtilsProperties = new InetUtilsProperties();

        InetUtils inetUtils = new InetUtils(inetUtilsProperties);

        ConsulDiscoveryProperties consulDiscoveryProperties = new ConsulDiscoveryProperties(inetUtils);

        String ipAddress = jsonObject.getString("ipAddress");
        String hostName = jsonObject.getString("hostName");
        String instanceZone = jsonObject.getString("instanceZone") ;
        String serverName = jsonObject.getString("serverName");

        //生产者的服务地址
        String address = jsonObject.getString("address");
        //生产者的服务端口
        Integer port = jsonObject.getInteger("port");

        consulDiscoveryProperties.setIpAddress(ipAddress);
        consulDiscoveryProperties.setRegister(true);
        consulDiscoveryProperties.setHostname(hostName);
        consulDiscoveryProperties.setInstanceZone(instanceZone);
        consulDiscoveryProperties.setServiceName(serverName);

        NewService newService = new NewService();
        newService.setId(UUID.randomUUID().toString());
        newService.setAddress(address);
        newService.setPort(port);
        newService.setName(serverName);
//        newService.set
        ConsulRegistration consulRegistration = new ConsulRegistration(newService,consulDiscoveryProperties);

        consulServiceRegistry.register(consulRegistration);

        return "";
    }
}
