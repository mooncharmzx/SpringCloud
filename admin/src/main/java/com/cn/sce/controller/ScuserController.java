package com.cn.sce.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.sce.admin.Result;
import com.cn.sce.entity.Scuser;
import com.cn.sce.service.ScuserService;
import com.cn.sce.test.util.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
        return scuserService.getScuserById(id);
    }

    @RequestMapping(value = "/getUserByName")
    public Result getUserByName(String params){
        JSONObject jsonObject = JSON.parseObject(params);
        return scuserService.getScuserByName(jsonObject.getString("username"));
    }

    @RequestMapping("/getUserByParams")
    public Result getUserByParams(String params){
        JSONObject jsonObject = JSON.parseObject(params);
        Map<String,Object> paMap = JSON.toJavaObject(jsonObject,Map.class);
        return scuserService.getScuserByParams(paMap);
    }

    @RequestMapping("/updateScuserByUserName")
    public Result updateScuserByUserName(@RequestBody String params){
        JSONObject jsonObject = JSON.parseObject(params);

        String username = jsonObject.getString("username");

        JSONObject scuserJson = jsonObject.getJSONObject("scuser");

        Scuser scuser= JSONObject.toJavaObject(scuserJson,Scuser.class);

        return scuserService.updateScuserByUserName(username,scuser);
//        return null;
    }

    @RequestMapping("/updateScuserByUserId")
    public Result updateScuserByUserId(@RequestBody String params){
        JSONObject jsonObject = JSON.parseObject(params);

        Integer userId = jsonObject.getInteger("userId");

        JSONObject scuserJson = jsonObject.getJSONObject("scuser");

        Scuser scuser= JSONObject.toJavaObject(scuserJson,Scuser.class);

        return scuserService.updateScuserByUserId(userId,scuser);
//        return null;
    }
}

