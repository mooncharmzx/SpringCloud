package com.cn.config;

import com.alibaba.fastjson.JSONObject;
import com.cn.model.UserD;
import com.cn.service.ScuserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UDService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UDService.class);

    private ScuserServiceImpl scuserService;

//    private final MenusService sceMenusService;

    public UDService(ScuserServiceImpl scuserService/*, MenusService sceMenusService*/) {
        this.scuserService = scuserService;
//        this.sceMenusService = sceMenusService;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        JSONObject jsonObject = scuserService.getScuserByName(login);

        return createSpringSecurityUser(login,jsonObject);

    }

    protected UserDetails createSpringSecurityUser(String lowercaseLogin, JSONObject data) {
        // if (!user.getActivated()) {
        // throw new UserNotActivatedException("User " + lowercaseLogin + " was
        // not
        // activated");
        // }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        JSONObject user = data.getJSONObject("data");
        Integer userId = user.getInteger("id");
        String username = user.getString("username");
        String password = user.getString("password");
        grantedAuthorities.add(new SimpleGrantedAuthority("admin"));

        return new UserD(userId, username, password, grantedAuthorities);
    }

}
