package com.back_community.api.user.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinDto {

    private String email;
    private String password;
    private String name;
    private String userClass;
    private String userTarget;

}
