package com.back_community.global.config;

public class SecurityWhiteList {

    private SecurityWhiteList() {} // 인스턴스화 방지

    public static final String[] PUBLIC_GET_URLS = {
            "/wake-up-log",
    };

    public static final String[] PUBLIC_URLS = {
            "/",
            "/login",
            "/join",
            "/docs/**",
            "/index.html",
            "/kakao/login",
            "/login/oauth2/code/google",
            "/oauth2/authorization/google"
    };
}
