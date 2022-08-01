package com.cn.util.jwt;

import com.cn.exception.IgnorePasswordEncoder;
import com.cn.util.SceProperties;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.json.Cas30JsonServiceTicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.Assert;

public class CasAuthenticationProvider implements AuthenticationProvider, InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(CasAuthenticationProvider.class);
    private final SceProperties ctsiProperties;
    private final UserDetailsService userDetailsService;

    public CasAuthenticationProvider(SceProperties ctsiProperties, UserDetailsService userDetailsService) {
        this.ctsiProperties = ctsiProperties;
        this.userDetailsService = userDetailsService;
    }

    public boolean supports(Class<?> authentication) {
        return CasAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CasAuthenticationToken casToken = (CasAuthenticationToken)authentication;
        String principal = null;
        Cas30JsonServiceTicketValidator validator = new Cas30JsonServiceTicketValidator(this.ctsiProperties.getSecurity().getAuthentication().getCas().getCasServerUrlPrefix());

        try {
            Assertion assertion = validator.validate(casToken.getPrincipal(), casToken.getServiceUrl());
            if (assertion != null && assertion.isValid()) {
                principal = assertion.getPrincipal().getName();
            }
        } catch (TicketValidationException var7) {
            throw new BadCredentialsException("TicketValidationException", var7);
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "");
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userDetailsService);
        provider.setPasswordEncoder(new IgnorePasswordEncoder());
        return provider.authenticate(token);
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
    }
}

