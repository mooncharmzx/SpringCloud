package com.cn.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.sce.entity.Result;
import com.cn.sce.entity.Scuser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class ScuserServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    public JSONObject getScuserByName(String username){

        String url = "http://GATEWAY/admin/wxoa/scuser/getUserByName";

        JSONObject paJson = new JSONObject();
        paJson.put("username",username);

        ResponseEntity<?> response = strTurnJson(paJson,url);

        return JSON.parseObject(response.getBody().toString());
    }

    public JSONObject updateScuserById(String id, Scuser scuser){
        String url = "http://GATEWAY/admin/wxoa/scuser/getUserByName";

        JSONObject paJson = new JSONObject();
        paJson.put("userId",id);
        paJson.put("scuser",scuser);
        ResponseEntity<?> response = strTurnJson(paJson,url);

        return JSON.parseObject(response.getBody().toString());
    }

    private ResponseEntity<?> strTurnJson(JSONObject paJson, String url) {
        List<Object> unList = new ArrayList<>();
        unList.add(JSON.toJSONString(paJson));
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>(1);
        params.put("params", unList);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        return restTemplate.postForEntity(url, request, JSONObject.class);
    }
}
