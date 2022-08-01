package com.cn.util.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CasAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 520L;
    private final String principal;
    private String credentials;
    private String serviceUrl;

    public CasAuthenticationToken(String serviceUrl, String principal, String credentials) {
        super((Collection)null);
        this.serviceUrl = serviceUrl;
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public CasAuthenticationToken(String serviceUrl, String principal, String credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.serviceUrl = serviceUrl;
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    public String getCredentials() {
        return this.credentials;
    }

    public String getPrincipal() {
        return this.principal;
    }

    public String getServiceUrl() {
        return this.serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
