package com.back_community.docs.wakeUpLog;

import com.back_community.api.wakeUpLog.comment.controller.WakeUpCommentController;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.CreateCommentDto;
import com.back_community.api.wakeUpLog.comment.service.WakeUpCommentService;
import com.back_community.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WakeUpLogCommentControllerDocsTest extends RestDocsSupport {

    private final WakeUpCommentService wakeUpCommentService = mock(WakeUpCommentService.class);

    @Override
    protected Object initController() {
        return new WakeUpCommentController(wakeUpCommentService);
    }

    @DisplayName("기상 게시물 댓글 작성 API 문서화")
    @Test
    void createComment() throws Exception {
        // given
        Long logId = 1L;
        CreateCommentDto commentDto = CreateCommentDto.builder().content("댓글 입니다.").build();

        // when then
        mockMvc.perform(post("/wake-up-log/{logId}/comment", logId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(commentDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-comment-create",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("logId").description("기상 게시물 ID")
                        ),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("댓글 작성 성공 메시지")
                        )
                ));
    }


}
