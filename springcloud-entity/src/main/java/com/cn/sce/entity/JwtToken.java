package com.cn.sce.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtToken {

    private String token;

    //
    private int passwordStatus;

    private int lockoutTime;

    private int attempts;
    JwtToken(String token) {
        this.token = token;
    }


    public JwtToken(String token, int passwordStatus) {
        this.token = token;
        this.passwordStatus = passwordStatus;
    }

    public JwtToken(int lockoutTime, int attempts) {
        this.lockoutTime = lockoutTime;
        this.attempts = attempts;
    }

    public JwtToken(int attempts) {
        this.attempts = attempts;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("passwordStatus")
    public int getPasswordStatus() {
        return passwordStatus;
    }

    public void setPasswordStatus(int passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    @JsonProperty("lockoutTime")
    public int getLockoutTime() {
        return lockoutTime;
    }

    public void setLockoutTime(int lockoutTime) {
        this.lockoutTime = lockoutTime;
    }

    @JsonProperty("attempts")
    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }
}

