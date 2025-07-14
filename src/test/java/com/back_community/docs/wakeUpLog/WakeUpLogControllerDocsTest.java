package com.back_community.docs.wakeUpLog;


import com.back_community.api.common.authentication.CustomUserPrincipal;
import com.back_community.api.wakeUpLog.board.controller.WakeUpLogController;
import com.back_community.api.wakeUpLog.board.domain.dto.request.CreateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.response.CreateWakeUpResponse;
import com.back_community.api.wakeUpLog.board.service.WakeUpLogService;
import com.back_community.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WakeUpLogControllerDocsTest extends RestDocsSupport {

    private final WakeUpLogService wakeUpLogService = mock(WakeUpLogService.class);

    @Override
    protected Object initController() {
        return new WakeUpLogController(wakeUpLogService);
    }

    @DisplayName("기상 기록 생성 API 문서화")
    @Test
    void createWakeUpLog() throws Exception {
        // given
        CreateWakeUpLogDto requestDto = CreateWakeUpLogDto.builder()
                .title("기상 기록")
                .content("오늘은 일찍 일어났어요")
                .build();

        CreateWakeUpResponse responseDto = CreateWakeUpResponse.createWakeUpSuccess(1L, 2);

        given(wakeUpLogService.createWakeUpLog(any(CreateWakeUpLogDto.class), anyLong()))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/wake-up-log")
                        .with(user(new CustomUserPrincipal(1L, "test@example.com")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-log-create",
                        preprocessRequest(prettyPrint()), // JSON 이쁘게 나옴
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("게시물 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("데이터 객체"),
                                fieldWithPath("data.wakeUpId").type(JsonFieldType.NUMBER).description("기상 기록 ID"),
                                fieldWithPath("data.wakeUpStreak").type(JsonFieldType.NUMBER).description("기상 연속 횟수")
                        )
                ));
    }


}
