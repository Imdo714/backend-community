package com.back_community.docs.wakeUpLog;

import com.back_community.api.wakeUpLog.likes.controller.WakeUpLikeController;
import com.back_community.api.wakeUpLog.likes.service.WakeUpLikeService;
import com.back_community.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WakeUpLikeControllerDocsTest extends RestDocsSupport {

    private final WakeUpLikeService wakeUpLikeService = mock(WakeUpLikeService.class);

    @Override
    protected Object initController() {
        return new WakeUpLikeController(wakeUpLikeService);
    }

    @DisplayName("기상 게시물 좋아요 등록 API 문서화")
    @Test
    void createComment() throws Exception {
        // given
        Long logId = 1L;

        // when then
        mockMvc.perform(post("/wake-up-log/{logId}/like", logId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-like-create",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("logId").description("기상 게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("좋아요 성공 메시지")
                        )
                ));
    }

    @DisplayName("기상 게시물 좋아요 취소 API 문서화")
    @Test
    void wakeUpCommentDelete() throws Exception {
        // given
        Long logId = 1L;

        // when then
        mockMvc.perform(
                        delete("/wake-up-log/{logId}/like", logId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-like-delete",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("logId").description("기상 게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("삭제 성공 메시지")
                        )
                ));
    }

}
