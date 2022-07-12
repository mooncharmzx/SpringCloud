package com.cn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.admin.Result;
import com.cn.config.redislock.RedisLock;
import com.cn.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    JedisConnectionFactory jedisConnectionFactory;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/redisPutString")
    public Result redisPutString(String params){
        Result result = new Result();
        JSONObject jsonObject = toJSON(params);
        String id = jsonObject.getString("id");
        String content = jsonObject.getString("content");
        jedisConnectionFactory.setDatabase(0);
        String oldString = "";
        if(redisTemplate.hasKey(id)){
            oldString = redisTemplate.opsForValue().get(id);
        }
        redisTemplate.opsForValue().set(id,content,300, TimeUnit.MINUTES);

        String newString = redisTemplate.opsForValue().get(id);
        result.setCode(0);
        result.setMsg("操作完成!");
        result.setData("目前缓存中的数据是:"+newString);
        return result;
    }

    @RequestMapping("/redisPutSet")
    public Result redisPutSet(String params){
        Result result = new Result();
        JSONObject jsonObject = toJSON(params);
        String id = jsonObject.getString("id");
        String content = jsonObject.getString("content");
        jedisConnectionFactory.setDatabase(1);
        String oldString = "";
        if(redisTemplate.hasKey(id)){
            oldString = redisTemplate.opsForValue().get(id);
        }
        redisTemplate.opsForValue().set(id,content,300, TimeUnit.MINUTES);

        String newString = redisTemplate.opsForValue().get(id);
        result.setCode(0);
        result.setMsg("操作完成!");
        result.setData("目前缓存中的数据是:"+newString);
        return result;
    }

    @RequestMapping("/redisPutList")
    public Result redisPutList(String params){
        Result result = new Result();
        result.setCode(0);
        JSONObject jsonObject = toJSON(params);
        String name = jsonObject.getString("name");
        JSONArray array = jsonObject.getJSONArray("array");
        jedisConnectionFactory.setDatabase(2);
        Integer integer = array.stream().map(Object::toString).map(Integer::parseInt).max(Comparator.comparing(i -> i))
                .get();
        if(redisTemplate.hasKey(name)){
            redisTemplate.opsForList().rightPush(name,integer.toString());
        }else{
            redisTemplate.opsForList().rightPush(name,integer.toString());
        }
        List<String> listOps = redisTemplate.opsForList().range(name,0,-1);;
        redisTemplate.expire(name,1,TimeUnit.HOURS);
        result.setMsg("成功!");
        result.setData("存redis list,并返回数据!!!!"+JSON.toJSONString(listOps));
        return result;
    }

    @RequestMapping("/redisPutJSON1")
    public Result redisPutJSON1(String params){
        Result result = new Result();
        result.setCode(0);
        JSONObject jsonObject = toJSON(params);
        String name = jsonObject.getString("name");
        JSONArray array = jsonObject.getJSONArray("array");
        jedisConnectionFactory.setDatabase(3);

//        if(redisTemplate.hasKey(name))
        redisTemplate.opsForValue().set(name,params);
        result.setMsg("成功!");
        result.setData("存redis!!!!");
        return result;
    }

    @RequestMapping("/redisPutJSON2")
    public Result redisPutJSON2(String params){
        Result result = new Result();
        result.setCode(0);
        JSONObject jsonObject = toJSON(params);
        String name = jsonObject.getString("name");
        JSONArray array = jsonObject.getJSONArray("array");
        jedisConnectionFactory.setDatabase(4);
        if(redisTemplate.hasKey(name)){
            redisTemplate.opsForValue().set(name,params,300,TimeUnit.MINUTES);
        }else{
            redisTemplate.opsForValue().set(name,params,300,TimeUnit.MINUTES);
        }
        result.setMsg("成功!");
        result.setData("存redis 并设置有效期!!!!");
        return result;
    }

    @RequestMapping("/queryData")
    public Result queryData(String id){
        try {
            //睡眠5s
            Thread.currentThread().sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Result result = new Result();
        result.setCode(0);
        result.setMsg("");
        return result;
    }

    @RequestMapping("/redisPutSetLock")
    @RedisLock(keepMills = 1000,index = 5)
    public Result redisPutSetLOck(String params){
        Result result = new Result();
        JSONObject jsonObject = toJSON(params);
        String id = jsonObject.getString("id");
        String content = jsonObject.getString("content");
        jedisConnectionFactory.setDatabase(5);
        String oldString = "";
        if(redisTemplate.hasKey(id)){
            oldString = redisTemplate.opsForValue().get(id);
        }
        redisTemplate.opsForValue().set(id,content,300, TimeUnit.MINUTES);

        String newString = redisTemplate.opsForValue().get(id);
        result.setCode(0);
        result.setMsg("操作完成!");
        result.setData("目前缓存中的数据是:"+newString);

        try {
            //睡眠5s
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping("queryRedisStringByKey")
    public Result queryRedisByKey(@RequestParam String redisKey){
        Result result = new Result();
        result.setCode(0);
        jedisConnectionFactory.setDatabase(2);
        String redisValue = "";
        if(redisTemplate.hasKey(redisKey)) {
            redisValue = redisTemplate.opsForValue().get(redisKey);
            result.setData(redisValue.trim());
            result.setMsg("查询完成!");
        }else{
            result.setMsg("redis key 不存在!");
            result.setCode(-1);
        }

        return result;
    }

    @RequestMapping("visitAdmin")
    public Result visitAdmin(String params){
        Result result = new Result();
//        restTemplate.postForEntity()
        return result;
    }

    private JSONObject toJSON(String json){

        if(ObjectUtils.notEmpty(json)){
            return JSON.parseObject(json);
        }
        return new JSONObject();
    }

}
