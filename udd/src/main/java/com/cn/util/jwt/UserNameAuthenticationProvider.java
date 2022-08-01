package com.cn.util.jwt;

import com.cn.exception.IgnorePasswordEncoder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserNameAuthenticationProvider implements AuthenticationProvider , InitializingBean {

    UserDetailsService userDetailsService;

    public UserNameAuthenticationProvider(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;

    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),
                StringUtils.EMPTY);

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(new IgnorePasswordEncoder());
        return provider.authenticate(token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
