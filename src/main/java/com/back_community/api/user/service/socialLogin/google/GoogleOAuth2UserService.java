package com.back_community.api.user.service.socialLogin.google;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class GoogleOAuth2UserService extends SimpleUrlAuthenticationSuccessHandler {

    private final GoogleSocialLoginService googleSocialLoginService;

    public GoogleOAuth2UserService(GoogleSocialLoginService googleSocialLoginService) {
        this.googleSocialLoginService = googleSocialLoginService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        String accessToken = googleSocialLoginService.googleSocialLogin(oAuth2User);

        Cookie jwtCookie = new Cookie("token", accessToken);
        jwtCookie.setPath("/"); //모든 경로에서 쿠키 사용가능
        response.addCookie(jwtCookie);
        response.sendRedirect("http://localhost:5173");
    }
}
