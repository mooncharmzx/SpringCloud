package com.cn.controller;

import com.cn.admin.Result;
import com.cn.config.SystemLog;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
@Component
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @SystemLog
    @RequestMapping("/queryData")
    @HystrixCommand(
            fallbackMethod = "queryDataFallback",// 服务降级方法
            // 使用commandProperties 可以配置熔断的一些细节信息
            commandProperties = {
                    // 类似kv形式
                    //这里这个参数意思是熔断超时时间2s，表示过了多长时间还没结束就进行熔断
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1000"),
                    // 当遇到失败后，开启一个11s的窗口
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds",value = "11000"),
                    // 最小请求数
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "1"),
                    // 失败次数占请求的50%
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),
                    // 跳闸后 活动窗口配置 这里配置了10s
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000")
            }

    )
    public Result queryData(String id){
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("id",id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://udd/test/queryData",request,String.class);
        responseEntity.getBody();
        Result result = new Result();
        result.setCode(1);
        result.setMsg("请求值2:"+responseEntity.getBody());
        return result;
    }

    public Result queryDataFallback(String id){
        Result result = new Result();
        result.setCode(1);
        result.setMsg("请求值1:"+id);
        return result;
    }

    @RequestMapping("/testAfterThrow")
    public Result testAfterThrow(String id){
        Result result = new Result();

        Integer.parseInt("a");

        result.setCode(1);
        result.setMsg("请求值1:"+id);
        return result;
    }

}
