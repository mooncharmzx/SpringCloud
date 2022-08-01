package com.cn.sce.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.sce.LoginCache;
import com.cn.sce.service.LoginService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginServiceImpl implements LoginService {

    private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Resource(
            name = "${sce.login.cache:guavaLoginCache}"
    )
    LoginCache loginCache;

    @Override
    public void setPass(String params){
        JSONObject parseObject = JSONObject.parseObject(params);
        String pass = parseObject.getString("params");
        System.out.println(passwordEncoder.encode(pass));
    }

    @Override
    public void openAccount(String params){
        JSONObject user = JSONObject.parseObject(params);

        String userName = user.getString("userName");

        String flag = user.getString("flag");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("loginFailed:");
        stringBuffer.append(userName);
        String countKey = stringBuffer.toString();

        Integer countKeyValue = null;
        if (this.loginCache != null) {
            countKeyValue = this.loginCache.get(countKey);

            if(countKeyValue != null){
                if(!StringUtils.isBlank(flag)){
                    switch (flag){
                        case "1":
                            logger.info("查询本地缓存中存放的登陆错误,错误"+countKeyValue+"次!");
                            break;
                        case "2":
                            logger.info("查询本地缓存中存放的登陆错误,错误"+countKeyValue+"次!");
                            this.loginCache.delete(countKey);
                            countKeyValue = this.loginCache.get(countKey);
                            if(countKeyValue == null){
                                logger.info("删除本地缓存中存放的登陆错误次数成功!");
                            }
                            break;
                        default:
                            logger.info("查询本地缓存中存放的登陆错误次数!flag参数值不存在!");
                    }
                }else{
                    logger.info("查询本地缓存中存放的登陆错误次数!flag参数未传");
                }
            }else{
                logger.info("查询用户名:"+userName+"本地缓存中存放的登陆错误次数!用户名未登陆过或是没有登陆错过!");
            }
        }
    }
}
