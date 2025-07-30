package com.back_community.api.user.controller;

import com.back_community.api.ApiResponse;
import com.back_community.api.user.domain.dto.request.JoinDto;
import com.back_community.api.user.domain.dto.request.LoginDto;
import com.back_community.api.user.domain.dto.response.LoginResponse;
import com.back_community.api.user.service.UserService;
import com.back_community.api.user.service.socialLogin.SocialLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SocialLoginService socialLoginService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody LoginDto loginDto) {
        return ApiResponse.ok(userService.loginAndGenerateToken(loginDto));
    }

    @PostMapping("/join")
    public ApiResponse<String> join(@RequestBody @Valid JoinDto joinDto) {
        userService.join(joinDto);
        return ApiResponse.ok("회원 가입 성공");
    }

    @PostMapping("/kakao/login")
    public ApiResponse<LoginResponse> aaaLogin(@RequestBody Map<String, String> payload) {
        String code = payload.get("code");
        log.info("code = {}", code);
        return ApiResponse.ok(socialLoginService.kakaoSocialLogin(code));
    }
}
