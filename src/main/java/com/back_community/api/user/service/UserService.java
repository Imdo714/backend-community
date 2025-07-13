package com.back_community.api.user.service;

import com.back_community.api.user.domain.dto.request.JoinDto;
import com.back_community.api.user.domain.dto.request.LoginDto;

public interface UserService {
    String loginAndGenerateToken(LoginDto loginDto);

    void join(JoinDto joinDto);
}
