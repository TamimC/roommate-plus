package com.tamimtechnology.roommateplus.security;

public class SecurityConstants {
    public static final String SIGNUP_URLS = "/api/users/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long TOKEN_EXPIRATION_TIME = 30_000;
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 9_000_000;
}

