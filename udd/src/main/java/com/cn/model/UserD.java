package com.cn.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserD  extends User implements UserDetails {

    private Integer userId;

    private String password;

    private String username;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {

        return (Collection<GrantedAuthority>) this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserD(Integer userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username,password,authorities);
        this.userId = userId;
        this.password = password;
        this.username = username;
        this.authorities = authorities;
    }
}
