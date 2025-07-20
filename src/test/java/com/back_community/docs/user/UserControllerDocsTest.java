package com.back_community.docs.user;

import com.back_community.api.user.controller.UserController;
import com.back_community.api.user.domain.dto.request.JoinDto;
import com.back_community.api.user.domain.dto.request.LoginDto;
import com.back_community.api.user.domain.dto.response.LoginResponse;
import com.back_community.api.user.service.UserService;
import com.back_community.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerDocsTest extends RestDocsSupport {

    private final UserService userService = mock(UserService.class);

    @Override
    protected Object initController() {
        return new UserController(userService);
    }

    @DisplayName("로그인 API 문서화")
    @Test
    void login() throws Exception {
        // given
        LoginDto loginDto = new LoginDto("test@example.com", "password");

        LoginResponse response = LoginResponse.builder().refreshToken("refreshToken 입니다.").build();

        given(userService.loginAndGenerateToken(any(LoginDto.class)))
                .willReturn(response);

        // when then
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).optional().description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).optional().description("비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.refreshToken").type(JsonFieldType.STRING).description("refreshToken")
                        )
                ));
    }

    @DisplayName("회원가입 API 문서화")
    @Test
    void join() throws Exception {
        // given
        JoinDto joinDto = new JoinDto("test@example.com", "password", "길동이", "KH-77기", "자바 마스터하기");

        // when then
        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user-join",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).optional().description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).optional().description("비밀번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).optional().description("이름"),
                                fieldWithPath("userClass").type(JsonFieldType.STRING).description("기수"),
                                fieldWithPath("userTarget").type(JsonFieldType.STRING).description("목표")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("회원가입 성공 메시지")
                        )
                ));
    }

}
