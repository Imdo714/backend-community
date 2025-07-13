package com.back_community.api.wakeUpLog.board.controller;

import com.back_community.api.ApiResponse;
import com.back_community.api.common.authentication.CustomUserPrincipal;
import com.back_community.api.wakeUpLog.board.domain.dto.request.CreateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.response.CreateWakeUpResponse;
import com.back_community.api.wakeUpLog.board.service.WakeUpLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WakeUpLogController {

    private final WakeUpLogService wakeUpLogService;

    @PostMapping("/wake-up-log")
    public ApiResponse<CreateWakeUpResponse> createWakeUpLog(@AuthenticationPrincipal CustomUserPrincipal userPrincipal,
                                                             @RequestBody CreateWakeUpLogDto createWakeUpLogDto){
        return ApiResponse.ok(wakeUpLogService.createWakeUpLog(createWakeUpLogDto, userPrincipal.getUserId()));
    }


}
