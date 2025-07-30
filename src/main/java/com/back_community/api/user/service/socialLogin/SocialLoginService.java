package com.back_community.api.user.service.socialLogin;

import com.back_community.api.user.domain.dto.response.LoginResponse;

public interface SocialLoginService {

    LoginResponse kakaoSocialLogin(String code);
}
