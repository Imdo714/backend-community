package com.back_community.docs.wakeUpLog;

import com.back_community.api.common.authentication.CustomUserPrincipal;
import com.back_community.api.common.page.PageInfo;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogListResponse;
import com.back_community.api.wakeUpLog.comment.controller.WakeUpCommentController;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.CreateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.UpdateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.response.CommentListResponse;
import com.back_community.api.wakeUpLog.comment.service.WakeUpCommentService;
import com.back_community.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                                fieldWithPath("content").type(JsonFieldType.STRING).optional().description("댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("댓글 작성 성공 메시지")
                        )
                ));
    }

    @DisplayName("기상 게시물 댓글 리스트 조회 API 문서화")
    @Test
    void wakeUpCommentList() throws Exception {
        // given
        Long logId = 1L;
        CommentListResponse.CommentList commentList = CommentListResponse.CommentList.builder()
                .commentId(3L)
                .imageUrl("http://img_Url")
                .content("댓글 입니다.")
                .createDate(LocalDateTime.now())
                .build();

        PageInfo pageInfo = new PageInfo(0, 5, 1, 5);

        CommentListResponse response = CommentListResponse.builder()
                .wakeUpCommentLists(List.of(commentList))
                .pageable(pageInfo)
                .build();

        given(wakeUpCommentService.getCommentList(any(), eq(0), eq(5)))
                .willReturn(response);

        // when & then
        mockMvc.perform(get("/wake-up-log/{logId}/comment", logId)
                        .with(user(new CustomUserPrincipal(1L, "test@example.com")))
                        .param("page", "0")
                        .param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-comment-list",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("logId").description("기상 게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),

                                fieldWithPath("data.wakeUpCommentLists").type(JsonFieldType.ARRAY).description("기상 게시물 댓글 리스트"),
                                fieldWithPath("data.wakeUpCommentLists[0].commentId").type(JsonFieldType.NUMBER).description("댓글 ID"),
                                fieldWithPath("data.wakeUpCommentLists[0].imageUrl").type(JsonFieldType.STRING).description("댓글 작성자 프로필"),
                                fieldWithPath("data.wakeUpCommentLists[0].content").type(JsonFieldType.STRING).description("댓글 내용"),
                                fieldWithPath("data.wakeUpCommentLists[0].createDate").type(JsonFieldType.ARRAY).description("댓글 생성일"),

                                fieldWithPath("data.pageable.totalElements").description("총 요소 수"),
                                fieldWithPath("data.pageable.totalPages").description("총 페이지 수"),
                                fieldWithPath("data.pageable.currentPage").description("현재 페이지"),
                                fieldWithPath("data.pageable.size").description("페이지 사이즈")
                        )
                ));
    }

    @DisplayName("기상 게시물 댓글 수정 API 문서화")
    @Test
    void wakeUpCommentUpdate() throws Exception {
        // given
        Long logId = 1L;
        Long commentId = 2L;
        UpdateCommentDto reqDto = UpdateCommentDto.builder().content("수정할 댓글 내용").build();

        // when then
        mockMvc.perform(
                patch("/wake-up-log/{logId}/comment/{commentId}", logId, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDto))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-comment-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("logId").description("기상 게시물 ID"),
                                parameterWithName("commentId").description("댓글 ID")
                        ),
                        requestFields(
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 댓글 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("수정 성공 메시지")
                        )
                ));
    }

    @DisplayName("기상 게시물 댓글 삭제 API 문서화")
    @Test
    void wakeUpCommentDelete() throws Exception {
        // given
        Long logId = 1L;
        Long commentId = 2L;

        // when then
        mockMvc.perform(
                        delete("/wake-up-log/{logId}/comment/{commentId}", logId, commentId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-comment-delete",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("logId").description("기상 게시물 ID"),
                                parameterWithName("commentId").description("댓글 ID")
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
