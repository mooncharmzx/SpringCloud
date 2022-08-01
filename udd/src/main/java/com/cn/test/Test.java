package com.cn.test;

import com.alibaba.fastjson.JSONObject;
import com.cn.model.UserD;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {

    private AuthenticationManager authenticationManager;
    public Test(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }
    public static void main(String[] args) {

        JSONObject user = new JSONObject();
        user.put("username","abc");
        user.put("id",2);
        user.put("password","");

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

//        JSONObject user = data.getJSONObject("data");
        Integer userId = user.getInteger("id");
        String username = user.getString("username");
        String password = user.getString("password");
        grantedAuthorities.add(new SimpleGrantedAuthority("admin"));

        UserD userD = new UserD(userId, username, password, grantedAuthorities);
        //校验账号、密码
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, password,grantedAuthorities);

        UsernamePasswordAuthenticationToken turn1 = (UsernamePasswordAuthenticationToken)authenticationToken;

        System.out.println(turn1);
    }
}
