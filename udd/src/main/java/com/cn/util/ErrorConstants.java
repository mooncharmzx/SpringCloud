package com.cn.util;

import java.net.URI;

public final class ErrorConstants {
    public static final String ERR_CONCURRENCY_FAILURE = "error.concurrencyFailure";
    public static final String ERR_VALIDATION = "error.validation";
    public static final String PROBLEM_BASE_URL = "problem";
    public static final URI DEFAULT_TYPE = URI.create("problem/message");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create("problem/constraint-violation");
    public static final URI PARAMETERIZED_TYPE = URI.create("problem/parameterized");
    public static final URI ENTITY_NOT_FOUND_TYPE = URI.create("problem/entity-not-found");
    public static final URI INVALID_PASSWORD_TYPE = URI.create("problem/invalid-password");
    public static final URI EMAIL_ALREADY_USED_TYPE = URI.create("problem/email-already-used");
    public static final URI LOGIN_ALREADY_USED_TYPE = URI.create("problem/login-already-used");
    public static final URI EMAIL_NOT_FOUND_TYPE = URI.create("problem/email-not-found");
    public static final URI CAPTCHA_TYPE = URI.create("problem/captcha");

    private ErrorConstants() {
    }
}
