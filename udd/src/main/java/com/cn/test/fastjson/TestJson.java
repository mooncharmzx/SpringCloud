package com.cn.test.fastjson;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestJson {
    public static void main(String[] args) {

        JSONObject params = new JSONObject();


        params.put("array",new JSONArray());

        setJsonObject(params);

        log.debug("新json object:{}",params);
    }

    public static void setJsonObject(JSONObject params){
        params.getJSONArray("array").add("添加一个值");


    }
}
