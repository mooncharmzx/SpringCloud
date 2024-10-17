package com.cn.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

/***            : TODO
 * @Copyright All rights Reserved, Designed By Circle Harmony Medical Insurance
 * @Project     ：SpringCloud
 * @Title       ：WxPublicUtil
 * @Description ：TODO
 * @Version     ：Ver1.0
 * @Author      ：lz
 * @Date        ：2024-10-17 14:28
 * @Dept        ：Information Technology Department
 * @Company     ：Circle Harmony Medical Insurance
 ***/
@Slf4j
@Component
public class WxPublicUtil {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity getWxPublic(String params) {

        JSONObject jsonParams = JSONObject.parseObject(params);

        String method = jsonParams.getString("method");
        String appId = jsonParams.getString("appId");
        String url = jsonParams.getString("url");
        String token = getToken(appId);
        url = url.contains("access_token_value")?url.replace("access_token_value",token):url;
        JSONObject parm = jsonParams.getJSONObject("param");
        ResponseEntity<String> entity;

        switch (method){
            case "POST":
                HttpHeaders headers = new HttpHeaders();
                restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
                MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
                headers.setContentType(type);
                HttpEntity<JSONObject> formEntity = new HttpEntity<JSONObject>(parm, headers);
                entity = restTemplate.postForEntity(url ,formEntity,String.class);
                break;
            case "GET":
                entity = restTemplate.getForEntity(url ,String.class);
                break;
            default:
                return ResponseEntity.ok("");
        }
        return ResponseEntity.ok(Objects.requireNonNull(entity.getBody()));
    }

    private String getToken(String appId) {
        try{
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
            url = url.replace("APPID","appId").replace("APPSECRET","");
            // 数据详情
            JSONObject params = new JSONObject();
            params.put("appId", appId);
            params.put("url",url);
            params.put("method","GET");
            ResponseEntity entity = getWxPublic(params.toJSONString());

            JSONObject jsonObject = JSONObject.parseObject((String) entity.getBody());
            JSONObject rootDta = jsonObject.getJSONObject("data");
            return rootDta.getString("access_token");
        }catch (Exception e) {
            log.error("", e);
            return e.toString();
        }
    }

}
