package com.cn.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl {

    @Autowired
    private RestTemplate restTemplate;

    public void setPass(String pass){
        String url = "http://GATEWAY/admin/login/setPass";

        strTurnJson(pass, url,MediaType.MULTIPART_FORM_DATA);
    }

    public void openAccount(String oa){
        String url = "http://GATEWAY/admin/login/openC";

        strTurnJson(oa, url,MediaType.APPLICATION_JSON);
    }

    private void strTurnJson(String param, String url,MediaType mediaType) {
        JSONObject paJson = JSONObject.parseObject(param);
        List<Object> unList = new ArrayList<>();
        unList.add(JSON.toJSONString(paJson));
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>(1);
        params.put("params", unList);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(params, headers);
        ResponseEntity<?> response = restTemplate.postForEntity(url, request, JSONObject.class);
    }

    public void updateUserDetailForLogin(String params) {
        String url = "http://GATEWAY/admin/wxoa/scuser/updateScuserByUserId";

        strTurnJson(params, url,MediaType.MULTIPART_FORM_DATA);
    }
}
