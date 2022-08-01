package com.cn.sce.config;

import com.cn.sce.LoginCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class GuavaLoginCache implements LoginCache {


    @Value("${sce.login.lockout-time:3600}")
    private int lockoutTime;


    /*
     * 缓存,适用于单节点部署
     */
    private LoadingCache<String, String> cache;
    private LoadingCache<String, Long> cacheTime;

    @PostConstruct
    public void initCache(){
        // CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
        // 在缓存不存在时通过CacheLoader的实现自动加载缓存
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(lockoutTime, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        // TODO Auto-generated method stub
                        return null;
                    }

                });
        cacheTime = CacheBuilder.newBuilder()
                .expireAfterWrite(lockoutTime, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Long>() {
                    @Override
                    public Long load(String key) throws Exception {
                        // TODO Auto-generated method stub
                        return null;
                    }

                });
    }


    @Override
    public boolean put(String key, Integer value) {
        try {
            cache.put(key, value.toString());
            cacheTime.put(key, DateTime.now().getMillis());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public Integer get(String key) {
        String str = null;
        try {
            // 获取value,验证码校验信息
            str = cache.get(key);
        } catch (Exception e) {
        }

        Integer resultValue = null;
        if(str!=null){

            resultValue = Integer.valueOf(str);
        }
        return resultValue;
    }

    @Override
    public boolean delete(String key) {
        try {
            cache.invalidate(key);
            cacheTime.invalidate(key);
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public Long getExpire(String key){
        Long second = null;
        Long time = null;
        try {
            time = cacheTime.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        if(time!=null){
            Long millis = DateTime.now().getMillis() - time;
            second = millis/1000;
            second = lockoutTime-second;
        }
        return second;
    }

}
