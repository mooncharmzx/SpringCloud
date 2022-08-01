package com.cn.controller;

import com.alibaba.fastjson.JSONObject;
import com.cn.admin.LoginUser;
import com.cn.config.RedisCache;
import com.cn.sce.IpUtil;
import com.cn.sce.LoginCache;
import com.cn.sce.RSAUtil;
import com.cn.sce.entity.JwtToken;
import com.cn.sce.entity.Result;
import com.cn.sce.entity.Scuser;
import com.cn.sce.exception.RestControllerException;
import com.cn.service.LoginServiceImpl;
import com.cn.service.ScuserServiceImpl;
import com.cn.util.SecurityUtils;
import com.cn.util.jwt.TokenProvider;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/system")
@Slf4j
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Value("${sce.RSA-prikey:}")
    private String rsaPrikey = "";
    @Value("${sce.login.bad-password-attempts:5}")
    private int badPasswordAttempts;
    @Value("${sce.login.lockout-time:3600}")
    private int lockoutTime;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource(
            name = "${sce.login.cache:guavaLoginCache}"
    )
    LoginCache loginCache;

    @Resource
    private LoginServiceImpl loginService;

    @Autowired
    private ScuserServiceImpl scuserService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisCache redisTemplate;

    @RequestMapping("/login")
    public ResponseEntity<JwtToken> login(HttpServletRequest request,@RequestBody String lu) {
        JSONObject jsonObject = new JSONObject();

        JSONObject luJson = JSONObject.parseObject(lu);

        String userName = luJson.getString("username");
        String passWord = luJson.getString("password");
        String logInSuccessKey = "loginSuccess:" + userName;
        String ipAddr = IpUtil.getIpAddr(request);

        log.info("-----------------" + ipAddr);

        //将前台传来的密码进行解密

        String decrypt = null;
        try {
            decrypt = RSAUtil.decrypt(passWord, RSAUtil.privateKey);
        } catch (Exception e) {

            log.error("登录后台RSA解密失败");
            throw new RestControllerException("密码错误");
        }

        String password = decryptPassword(decrypt);
//        String password = passWord;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("loginFailed:");
        stringBuffer.append(userName);
        String countKey = stringBuffer.toString();
        Integer countKeyValue = null;

        if (this.loginCache != null) {
            countKeyValue = this.loginCache.get(countKey);
            if (countKeyValue != null && countKeyValue == this.badPasswordAttempts) {
                long expire = this.loginCache.getExpire(countKey);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtToken((int) expire, 0));
            }
        }
        //校验账号有效性
        //校验账号、密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, password);
        Authentication authentication = null;

        try {
            authentication = this.authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException var15) {
            if (this.loginCache != null) {
                int countKeyLeft = -1;
                if (countKeyValue == null) {
                    this.loginCache.put(countKey, 1);
                    countKeyLeft = this.badPasswordAttempts - 1;
                } else if (countKeyValue < this.badPasswordAttempts) {
                    this.loginCache.put(countKey, countKeyValue + 1);
                    countKeyLeft = this.badPasswordAttempts - 1 - countKeyValue;
                    if (countKeyLeft == 0) {
                        this.loginCache.put(countKey, this.badPasswordAttempts);
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtToken(this.lockoutTime, countKeyLeft));
                    }
                }

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new JwtToken(countKeyLeft));
            }
            throw var15;
        }
        if (this.loginCache != null && countKeyValue != null) {
            this.loginCache.delete(countKey);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = this.tokenProvider.createToken(authentication, true);
        SecurityUtils.getOptionalCurrentUserId().map((userId) -> {

            JSONObject params = new JSONObject();
            params.put("userId",userId);

            Scuser scuser = new Scuser();
            scuser.setLoginState(1);

            params.put("scuser",scuser);
            this.loginService.updateUserDetailForLogin(params.toJSONString());
            return null;
        });
        HttpHeaders httpHeaders = new HttpHeaders();
        String token = "Bearer " + jwt;
//        int status = this.scuserService.passwordNeedChange(SecurityUtils.getCurrentUserId(), password);
//        if (status == 0) {
            httpHeaders.add("Authorization", token);

            //登录成功redis里面存token

        redisTemplate.setCacheObject(logInSuccessKey, token);

            //将账号是否脱敏存入本地缓存提高速度
//            if (this.loginCache != null) {
//
//                int currentUserId = SecurityUtils.getCurrentUserId();
//
//                User currentUser = userRepository.findByCscpUserId(currentUserId);
//                currentUser.setUpdateTime(new Date());//更新登陆时间
//                if(currentUser.getExpire()==0){
//                    currentUser.setToken(UUIDUtil.getUUID());
//                }
//                userRepository.save(currentUser);
//                if(currentUser.getDesensitization()!=null&&currentUser.getDesensitization()==1){
//                    this.loginCache.put("sce"+currentUserId,currentUser.getDesensitization());
//                }else {
//                    this.loginCache.delete("sce"+currentUserId);
//                }
//
//            }

            return new ResponseEntity(new JwtToken(token, 0), httpHeaders, HttpStatus.OK);
//        } else {
//            return new ResponseEntity(new UserJwtController.JwtToken("", status), httpHeaders, HttpStatus.OK);
//        }
    }

    private String decryptPassword(String ciphertext) {
        String password = ciphertext;

        try {
            if (StringUtils.isNotBlank(this.rsaPrikey)) {
                password = new String(RSAUtil.decryptPri(Base64.decodeBase64(ciphertext), Base64.decodeBase64(this.rsaPrikey)));
            }
        } catch (Exception var4) {
            this.logger.error(var4.getMessage());
        }

        return password;
    }

    @RequestMapping("/setPass")
    public void setPass(@RequestBody String params) {

        loginService.setPass(params);
    }

//    @RequestMapping("/openC")
//    public void openAccount(@RequestBody String params){
//        loginService.openAccount(params);
//    }

    @RequestMapping("/openC")
    public void openAccount(@RequestBody String params){
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

    public static class JwtToken {

        private String token;



        //
        private int passwordStatus;

        private int lockoutTime;

        private int attempts;
        JwtToken(String token) {
            this.token = token;
        }


        public JwtToken(String token, int passwordStatus) {
            this.token = token;
            this.passwordStatus = passwordStatus;
        }

        public JwtToken(int lockoutTime, int attempts) {
            this.lockoutTime = lockoutTime;
            this.attempts = attempts;
        }

        public JwtToken(int attempts) {
            this.attempts = attempts;
        }

        @JsonProperty("token")
        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @JsonProperty("passwordStatus")
        public int getPasswordStatus() {
            return passwordStatus;
        }

        public void setPasswordStatus(int passwordStatus) {
            this.passwordStatus = passwordStatus;
        }

        @JsonProperty("lockoutTime")
        public int getLockoutTime() {
            return lockoutTime;
        }

        public void setLockoutTime(int lockoutTime) {
            this.lockoutTime = lockoutTime;
        }

        @JsonProperty("attempts")
        public int getAttempts() {
            return attempts;
        }

        public void setAttempts(int attempts) {
            this.attempts = attempts;
        }
    }
}
