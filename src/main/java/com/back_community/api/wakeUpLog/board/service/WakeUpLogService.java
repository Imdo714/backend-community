package com.back_community.api.wakeUpLog.board.service;

import com.back_community.api.common.authentication.CustomUserPrincipal;
import com.back_community.api.wakeUpLog.board.domain.dto.request.CreateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.response.CreateWakeUpResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogListResponse;

public interface WakeUpLogService {
    CreateWakeUpResponse createWakeUpLog(CreateWakeUpLogDto createWakeUpLogDto, Long userId);

    WakeUpLogListResponse getWakeUpLogList(CustomUserPrincipal userPrincipal, int page, int size);

}
