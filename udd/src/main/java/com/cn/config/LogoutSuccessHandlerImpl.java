package com.cn.config;

import com.alibaba.fastjson.JSON;
import com.cn.admin.AjaxResult;
import com.cn.admin.LoginUser;
import com.cn.util.ObjectUtils;
import com.cn.util.ServletUtils;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @author ruoyi
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(httpServletRequest);
        if (ObjectUtils.notEmpty(loginUser))
        {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
        }
        ServletUtils.renderString(httpServletResponse, JSON.toJSONString(AjaxResult.error(HttpStatus.OK.value(), "退出成功")));
    }
}
