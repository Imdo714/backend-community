package com.back_community.docs.wakeUpLog;


import com.back_community.api.common.authentication.CustomUserPrincipal;
import com.back_community.api.common.page.PageInfo;
import com.back_community.api.wakeUpLog.board.controller.WakeUpLogController;
import com.back_community.api.wakeUpLog.board.domain.dto.request.CreateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.request.UpdateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.request.WakeUpListDto;
import com.back_community.api.wakeUpLog.board.domain.dto.response.CreateWakeUpResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogDetailResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogListResponse;
import com.back_community.api.wakeUpLog.board.service.WakeUpLogService;
import com.back_community.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
                                fieldWithPath("title").type(JsonFieldType.STRING).optional().description("게시물 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).optional().description("게시물 내용")
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

    @DisplayName("기상 기록 리스트 API 문서화")
    @Test
    void wakeUpLogList() throws Exception {
        // given
        WakeUpListDto wakeUpList = WakeUpListDto.builder()
                .wakeUpId(1L)
                .title("기상 기록")
                .createDate(LocalDateTime.now())
                .likeCount(1L)
                .commentCount(4L)
                .build();

        PageInfo pageInfo = new PageInfo(0, 5, 1, 5);

        WakeUpLogListResponse response = WakeUpLogListResponse.builder()
                .wakeUpLists(List.of(wakeUpList))
                .pageable(pageInfo)
                .build();

        given(wakeUpLogService.getWakeUpLogList(eq(0), eq(5))).willReturn(response);

        // when & then
        mockMvc.perform(get("/wake-up-log")
                        .with(user(new CustomUserPrincipal(1L, "test@example.com")))
                        .param("page", "0")
                        .param("size", "5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-log-list",
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),

                                fieldWithPath("data.wakeUpLists").type(JsonFieldType.ARRAY).description("기상 기록 리스트"),
                                fieldWithPath("data.wakeUpLists[0].wakeUpId").type(JsonFieldType.NUMBER).description("기상 기록 ID"),
                                fieldWithPath("data.wakeUpLists[0].userName").type(JsonFieldType.STRING).description("작성자 이름"),
                                fieldWithPath("data.wakeUpLists[0].imageUrl").type(JsonFieldType.STRING).description("작성자 프로필"),
                                fieldWithPath("data.wakeUpLists[0].title").type(JsonFieldType.STRING).description("기상 제목"),
                                fieldWithPath("data.wakeUpLists[0].createDate").type(JsonFieldType.ARRAY).description("기록 생성일"),
                                fieldWithPath("data.wakeUpLists[0].likeCount").type(JsonFieldType.BOOLEAN).description("좋아요 수"),
                                fieldWithPath("data.wakeUpLists[0].commentCount").type(JsonFieldType.BOOLEAN).description("댓글 수"),

                                fieldWithPath("data.pageable.totalElements").description("총 요소 수"),
                                fieldWithPath("data.pageable.totalPages").description("총 페이지 수"),
                                fieldWithPath("data.pageable.currentPage").description("현재 페이지"),
                                fieldWithPath("data.pageable.size").description("페이지 사이즈")
                        )
                ));
    }

    @DisplayName("기상 게시물 상세 조회 API 문서화")
    @Test
    void wakeUpLogDetail() throws Exception {
        // given
        Long logId = 1L;

        WakeUpLogDetailResponse response = WakeUpLogDetailResponse.builder()
                .title("기상 기록 제목")
                .content("기상 내용")
                .createDate(LocalDateTime.of(2025, 7, 14, 7, 0))
                .likesCount(10)
                .build();

        given(wakeUpLogService.wakeUpLogDetail(logId)).willReturn(response);

        // when then
        mockMvc.perform(get("/wake-up-log/{logId}", logId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-log-detail",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("logId").description("기상 게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("기상 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("기상 내용"),
                                fieldWithPath("data.createDate").type(JsonFieldType.ARRAY).description("작성일시"),
                                fieldWithPath("data.likesCount").type(JsonFieldType.NUMBER).description("좋아요 수")
                        )
                ));
    }

    @DisplayName("기상 게시물 수정 API 문서화")
    @Test
    void wakeUpLogUpdate() throws Exception {
        // given
        Long logId = 1L;
        UpdateWakeUpLogDto requestDto = UpdateWakeUpLogDto.builder()
                .title("기상 기록 제목 수정함")
                .content("기상 내용 수정함")
                .build();

        WakeUpLogDetailResponse response = WakeUpLogDetailResponse.builder()
                .title("기상 기록 제목 수정함")
                .content("기상 내용 수정함")
                .createDate(LocalDateTime.of(2025, 7, 14, 7, 0))
                .likesCount(10)
                .build();

        given(wakeUpLogService.wakeUpLogUpdate(eq(logId), anyLong(), any(UpdateWakeUpLogDto.class)))
                .willReturn(response);

        // when then
        mockMvc.perform(patch("/wake-up-log/{logId}", logId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-log-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("logId").description("기상 게시물 ID")
                        ),
                        requestFields(
                                fieldWithPath("title").type(JsonFieldType.STRING).description("수정할 기상 제목"),
                                fieldWithPath("content").type(JsonFieldType.STRING).description("수정할 기상 내용")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data.title").type(JsonFieldType.STRING).description("기상 제목"),
                                fieldWithPath("data.content").type(JsonFieldType.STRING).description("기상 내용"),
                                fieldWithPath("data.createDate").type(JsonFieldType.ARRAY).description("작성일시"),
                                fieldWithPath("data.likesCount").type(JsonFieldType.NUMBER).description("좋아요 수")
                        )
                ));
    }

    @DisplayName("기상 게시물 삭제 API 문서화")
    @Test
    void wakeUpLogDelete() throws Exception {
        // given
        Long logId = 1L;

        // when then
        mockMvc.perform(delete("/wake-up-log/{logId}", logId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("wake-up-log-delete",
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("logId").description("기상 게시물 ID")
                        ),
                        responseFields(
                                fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.STRING).description("삭세 성공 메시지")
                        )
                ));
    }

}
