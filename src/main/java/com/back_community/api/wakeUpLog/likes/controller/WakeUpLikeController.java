package com.back_community.api.wakeUpLog.likes.controller;

import com.back_community.api.ApiResponse;
import com.back_community.api.common.authentication.CustomUserPrincipal;
import com.back_community.api.wakeUpLog.likes.service.WakeUpLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WakeUpLikeController {

    private final WakeUpLikeService wakeUpLikeService;

    @PostMapping("/wake-up-log/{logId}/like")
    public ApiResponse<?> createLike(@PathVariable Long logId,
                                     @AuthenticationPrincipal CustomUserPrincipal userPrincipal){
        wakeUpLikeService.createLike(logId, userPrincipal.getUserId());
        return ApiResponse.of(HttpStatus.OK, "좋아요 성공");
    }

    @DeleteMapping("/wake-up-log/{logId}/like")
    public ApiResponse<?> deleteLike(@PathVariable Long logId,
                                     @AuthenticationPrincipal CustomUserPrincipal userPrincipal){
        wakeUpLikeService.deleteLike(logId, userPrincipal.getUserId());
        return ApiResponse.of(HttpStatus.OK, "좋아요 취소 성공");
    }

}
