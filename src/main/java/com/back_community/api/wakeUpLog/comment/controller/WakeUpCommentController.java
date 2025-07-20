package com.back_community.api.wakeUpLog.comment.controller;

import com.back_community.api.ApiResponse;
import com.back_community.api.common.authentication.CustomUserPrincipal;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.CreateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.UpdateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.response.CommentListResponse;
import com.back_community.api.wakeUpLog.comment.service.WakeUpCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WakeUpCommentController {

    private final WakeUpCommentService wakeUpCommentService;

    @PostMapping("/wake-up-log/{logId}/comment")
    public ApiResponse<?> createComment(@PathVariable Long logId,
                                        @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
                                        @RequestBody @Valid CreateCommentDto createCommentDto){

        wakeUpCommentService.createComment(createCommentDto, userPrincipal.getUserId(), logId);
        return ApiResponse.of(HttpStatus.OK, "댓글 작성 성공");
    }

    @GetMapping("/wake-up-log/{logId}/comment")
    public ApiResponse<CommentListResponse> wakeUpCommentList(@PathVariable Long logId,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size){
        return ApiResponse.ok(wakeUpCommentService.getCommentList(logId, page, size));
    }

    @PatchMapping("/wake-up-log/{logId}/comment/{commentId}")
    public ApiResponse<?> wakeUpCommentUpdate(@PathVariable Long logId,
                                              @PathVariable Long commentId,
                                              @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
                                              @RequestBody UpdateCommentDto updateCommentDto){
        wakeUpCommentService.getCommentUpdate(userPrincipal.getUserId(), logId, commentId, updateCommentDto);
        return ApiResponse.of(HttpStatus.OK, "댓글 수정 성공");
    }

    @DeleteMapping("/wake-up-log/{logId}/comment/{commentId}")
    public ApiResponse<?> wakeUpCommentDelete(@PathVariable Long logId,
                                              @PathVariable Long commentId,
                                              @AuthenticationPrincipal CustomUserPrincipal userPrincipal){
        wakeUpCommentService.getCommentDelete(logId, commentId, userPrincipal.getUserId());
        return ApiResponse.of(HttpStatus.OK, "댓글 삭제 성공");
    }

}
