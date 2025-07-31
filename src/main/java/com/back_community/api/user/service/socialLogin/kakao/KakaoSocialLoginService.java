package com.back_community.api.user.service.socialLogin.kakao;

import com.back_community.api.user.domain.dto.response.LoginResponse;

public interface KakaoSocialLoginService {

    LoginResponse kakaoSocialLogin(String code);
}
