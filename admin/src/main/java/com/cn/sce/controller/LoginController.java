package com.cn.sce.controller;

import com.alibaba.fastjson.JSONObject;
import com.cn.sce.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/setPass")
    public void setPass(@RequestParam String params) {
        loginService.setPass(params);
    }

    @RequestMapping("/openC")
    public void openAccount(@RequestBody String params){
        JSONObject parseObject = JSONObject.parseObject(params);
        String str = parseObject.getJSONArray("params").getString(0);
        loginService.openAccount(str);
    }

}
