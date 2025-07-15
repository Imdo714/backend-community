package com.back_community.api.user.service;

import com.back_community.api.user.domain.dto.request.JoinDto;
import com.back_community.api.user.domain.dto.request.LoginDto;
import com.back_community.api.user.domain.dto.response.LoginResponse;

public interface UserService {
    LoginResponse loginAndGenerateToken(LoginDto loginDto);

    void join(JoinDto joinDto);
}
