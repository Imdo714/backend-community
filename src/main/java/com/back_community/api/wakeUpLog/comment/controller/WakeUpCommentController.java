package com.back_community.api.wakeUpLog.comment.controller;

import com.back_community.api.ApiResponse;
import com.back_community.api.common.authentication.CustomUserPrincipal;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.CreateCommentDto;
import com.back_community.api.wakeUpLog.comment.service.WakeUpCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WakeUpCommentController {

    private final WakeUpCommentService wakeUpCommentService;

    @PostMapping("/wake-up-log/{logId}/comment")
    public ApiResponse<?> createComment(@PathVariable Long logId,
                                        @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
                                        @RequestBody CreateCommentDto createCommentDto){

        wakeUpCommentService.createComment(createCommentDto, userPrincipal.getUserId(), logId);
        return ApiResponse.of(HttpStatus.OK, "댓글 작성 성공");
    }

}
