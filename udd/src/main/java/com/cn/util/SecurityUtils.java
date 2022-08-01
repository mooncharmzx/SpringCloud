package com.cn.util;

import com.cn.admin.LoginUser;
import com.cn.exception.CustomException;
import com.cn.sce.exception.RestControllerException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

/**
 * 安全服务工具类
 *
 * @author ruoyi
 */
public class SecurityUtils
{
    /**
     * 获取用户账户
     **/
    public static String getUsername() throws CustomException {
        try
        {
            return getLoginUser().getUsername();
        }
        catch (Exception e)
        {
            throw new CustomException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser() throws CustomException {
        try
        {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e)
        {
            throw new CustomException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public static Optional<Integer> getOptionalCurrentUserId() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication()).map((authentication) -> {
            if (authentication.getPrincipal() instanceof LoginUser) {
                LoginUser springSecurityUser = (LoginUser)authentication.getPrincipal();
                return springSecurityUser.getId();
            } else {
                return null;
            }
        });
    }

    public static int getCurrentUserId() {
        int userId = getOptionalCurrentUserId().map((id) -> {
            return id;
        }).orElse(0);

        if(userId==0){

            throw new RestControllerException("用户登录信息失效，请重新登录");
        }else {
            return userId;
        }
    }
}
