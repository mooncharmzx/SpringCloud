package com.cn.config;

import com.cn.model.UserD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UDService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UDService.class);

//    private final UserService userService;
//    private final MenusService cscpMenusService;

    public UDService(/*UserService userService, MenusService cscpMenusService*/) {
//        this.userService = userService;
//        this.cscpMenusService = cscpMenusService;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        UserDetails userDetails = new UserD();
        return userDetails;

    }

    protected UserDetails createSpringSecurityUser(String lowercaseLogin, User user) {
        // if (!user.getActivated()) {
        // throw new UserNotActivatedException("User " + lowercaseLogin + " was
        // not
        // activated");
        // }

        UserDetails userDetails = new UserD();
        return userDetails;
    }

}
