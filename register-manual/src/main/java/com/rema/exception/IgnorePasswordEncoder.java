package com.rema.exception;

import org.springframework.security.crypto.password.PasswordEncoder;

public class IgnorePasswordEncoder implements PasswordEncoder {
    public IgnorePasswordEncoder() {
    }

    public String encode(CharSequence rawPassword) {
        return null;
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return true;
    }
}
