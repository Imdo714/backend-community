package com.back_community.api.user.service.socialLogin.google;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface GoogleSocialLoginService {

    String googleSocialLogin(OAuth2User oAuth2User);
}
