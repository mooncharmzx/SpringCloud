package com.cn.controller;

import com.cn.admin.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/getUser")
    public Result getUser(){
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功!");
        result.setData("第一个请求数据!!!!");
        return result;
    }
}
