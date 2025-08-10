package com.back_community.api.wakeUpLog.board.controller;

import com.back_community.api.ApiResponse;
import com.back_community.api.common.authentication.CustomUserPrincipal;
import com.back_community.api.wakeUpLog.board.domain.dto.request.CreateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.request.UpdateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.response.CreateWakeUpResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogDetailResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogListResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WriteWakeUpTop3;
import com.back_community.api.wakeUpLog.board.service.WakeUpLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WakeUpLogController {

    private final WakeUpLogService wakeUpLogService;

    @PostMapping("/wake-up-log")
    public ApiResponse<CreateWakeUpResponse> createWakeUpLog(@AuthenticationPrincipal CustomUserPrincipal userPrincipal,
                                                             @ModelAttribute @Valid CreateWakeUpLogDto createWakeUpLogDto,
                                                             @RequestParam(value = "image", required = false) MultipartFile image){
        return ApiResponse.ok(wakeUpLogService.createWakeUpLog(createWakeUpLogDto, image, userPrincipal.getUserId()));
    }

    @GetMapping("/wake-up-log")
    public ApiResponse<WakeUpLogListResponse> wakeUpLogList(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size){
        return ApiResponse.ok(wakeUpLogService.getWakeUpLogList(page, size));
    }

    @GetMapping("/wake-up-log/{logId}")
    public ApiResponse<WakeUpLogDetailResponse> wakeUpLogDetail(@PathVariable Long logId,
                                                                @AuthenticationPrincipal CustomUserPrincipal userPrincipal){
        Long userId = userPrincipal != null ? userPrincipal.getUserId() : null;
        return ApiResponse.ok(wakeUpLogService.wakeUpLogDetail(logId, userId));
    }

    @PatchMapping("/wake-up-log/{logId}")
    public ApiResponse<WakeUpLogDetailResponse> wakeUpLogUpdate(@PathVariable Long logId,
                                                                @AuthenticationPrincipal CustomUserPrincipal userPrincipal,
                                                                @RequestBody UpdateWakeUpLogDto updateWakeUpLogDto){
        return ApiResponse.ok(wakeUpLogService.wakeUpLogUpdate(logId, userPrincipal.getUserId(), updateWakeUpLogDto));
    }

    @DeleteMapping("/wake-up-log/{logId}")
    public ApiResponse<?> wakeUpLogDelete(@PathVariable Long logId,
                                          @AuthenticationPrincipal CustomUserPrincipal userPrincipal){
        wakeUpLogService.wakeUpLogDelete(logId, userPrincipal.getUserId());
        return ApiResponse.of(HttpStatus.OK, "삭제 성공");
    }

    @GetMapping("/wake-up-log/rank")
    public ApiResponse<WriteWakeUpTop3> wakeUpRank(){
        return ApiResponse.ok(wakeUpLogService.getWriteWakeUpRank());
    }

}
