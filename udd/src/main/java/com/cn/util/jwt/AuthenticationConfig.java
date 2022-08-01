package com.cn.util.jwt;

import java.util.Arrays;
import java.util.List;

import com.cn.config.UDService;
import com.cn.service.ScuserServiceImpl;
import com.cn.util.SceProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.SystemWideSaltSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableConfigurationProperties({SceProperties.class})
public class AuthenticationConfig {

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public CasAuthenticationProvider casAuthenticationProvider(SceProperties sceProperties,
                                                               UserDetailsService userDetailsService) {
        return new CasAuthenticationProvider(sceProperties, userDetailsService);
    }

    @Bean
    @ConditionalOnMissingBean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
                                                               UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    /*@Bean
    @ConditionalOnMissingBean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
                                                       DaoAuthenticationProvider daoAuthenticationProvider, CasAuthenticationProvider casAuthenticationProvider) {
        List<AuthenticationProvider> providers = Arrays
                .asList(daoAuthenticationProvider, casAuthenticationProvider);
        return new ProviderManager(providers);
    }*/

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(ScuserServiceImpl scuserService/*, CscpMenusService cscpMenusService*/) {

        return new UDService(scuserService/*, cscpMenusService*/);

    }
}
