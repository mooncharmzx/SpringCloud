package com.cn.util.jwt;

import com.alibaba.fastjson.JSONObject;
import com.cn.admin.LoginUser;
import com.cn.config.RedisCache;
import com.cn.config.TokenService;
import com.cn.sce.LoginCache;
import com.cn.service.ScuserServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * Filters incoming requests and installs a Spring Security principal if a header corresponding to a valid user is
 * found.
 */
public class JWTFilter extends GenericFilterBean {

    private TokenProvider tokenProvider;

    private ScuserServiceImpl userRepository;

    private UserNameAuthenticationProvider userNameAuthenticationProvider;

    private LoginCache loginCache;

    private TokenService tokenService;

    public JWTFilter(TokenProvider tokenProvider, TokenService tokenService,
                     ScuserServiceImpl userRepository, UserNameAuthenticationProvider userNameAuthenticationProvider, LoginCache loginCache) {
        this.tokenProvider = tokenProvider;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.userNameAuthenticationProvider = userNameAuthenticationProvider;
        this.loginCache = loginCache;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String bearerToken = httpServletRequest.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);


        //处理大屏token

//        if (StringUtils.hasText(bearerToken) && bearerToken.length() == 32) {
//
//            //根据token查询对应的用户
//            JSONObject user = userRepository.findByToken(bearerToken);
//
//            if (user != null) {
//
//                UsernameAuthenticationToken authenticationToken = new UsernameAuthenticationToken(user.getCscpUser().getUsername());
//                Authentication authentication = userNameAuthenticationProvider.authenticate(authenticationToken);
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//                if (this.loginCache != null) {
//
//
//                    if (user.getDesensitization() != null && user.getDesensitization() == 1) {
//
//                        this.loginCache.put("sce" + ((CscpUserDetail) authentication.getPrincipal()).getId(), user.getDesensitization());
//
//                    }
//
//
//                }
//
//
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }
//
//
//        }


        if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
            Authentication authentication = this.tokenProvider.getAuthentication(jwt);
            String logInSuccessKey = "loginSuccess:" + authentication.getName();
            //redis中的token
            //1.redis中不存在该用户的token -> 这种情况较少，基本不存在
            //2.redis中存在该用户的token，且与当前token相等 -> 当前登录合法
            //3.redis中存在该用户的token，但与当前token不相等 -> 当前token已失效

            JSONObject jsonObject = userRepository.getScuserByName(authentication.getName());

            JSONObject data = jsonObject.getJSONObject("data");

//            if ("admin_bzjhsq1".equals(authentication.getName())||"admin_bzjhxq1".equals(authentication.getName())||"admin_zhsq".equals(authentication.getName()) || "zhujianhx".equals(authentication.getName()) || "NJZTWYAdmin".equals(authentication.getName())) {
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//                filterChain.doFilter(servletRequest, servletResponse);
//                return;
//            }

            LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
            if (!Objects.isNull(loginUser)) {
                String existToken = String.valueOf(loginUser.getToken());
                if (bearerToken.equals(existToken)) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JWTConfigurer.AUTHORIZATION_BEARER)) {
            return bearerToken.substring(JWTConfigurer.AUTHORIZATION_BEARER.length(), bearerToken.length());
        }
        return null;
    }
}
