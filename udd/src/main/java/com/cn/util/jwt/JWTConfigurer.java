
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.cn.util.jwt;

import com.cn.config.TokenService;
import com.cn.sce.LoginCache;
import com.cn.service.ScuserServiceImpl;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_BEARER = "Bearer ";
    private TokenProvider tokenProvider;
    private TokenService tokenService;
    private ScuserServiceImpl userRepository;
    private UserNameAuthenticationProvider userNameAuthenticationProvider;
    private LoginCache loginCache;

    public JWTConfigurer(TokenProvider tokenProvider, TokenService tokenService,ScuserServiceImpl userRepository,UserNameAuthenticationProvider userNameAuthenticationProvider,LoginCache loginCache) {
        this.tokenProvider = tokenProvider;
        this.tokenService=tokenService;
        this.userRepository=userRepository;
        this.userNameAuthenticationProvider=userNameAuthenticationProvider;
        this.loginCache=loginCache;
    }

    public void configure(HttpSecurity http) throws Exception {
        JWTFilter customFilter = new JWTFilter(this.tokenProvider,this.tokenService,this.userRepository,this.userNameAuthenticationProvider,this.loginCache);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
