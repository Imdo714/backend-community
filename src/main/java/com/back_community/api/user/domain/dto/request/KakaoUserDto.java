package com.back_community.api.user.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserDto {

    private String email;
    private String nickname;

}
