package com.cn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.admin.Result;
import com.cn.service.ScuserService;
import com.cn.test.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lz
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/wxoa/scuser")
public class ScuserController extends BaseController {

    @Autowired
    private ScuserService scuserService;

    @RequestMapping("/getUser")
    public Result getUser(String params){
        JSONObject jsonObject = JSON.parseObject(params);
        String id = jsonObject.getString("id");
        Result result = scuserService.getScuserById(id);
        return result;
    }

}

