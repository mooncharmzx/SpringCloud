package com.cn.sce.controller;

import com.cn.sce.admin.Result;
import com.cn.sce.service.ScuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ScuserService scuserService;

    @RequestMapping("/getUser")
    public Result getUser(@RequestParam String id){
        Result result = scuserService.getScuserById(id);
        return result;
    }
}
