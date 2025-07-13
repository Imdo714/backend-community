package com.back_community.api.wakeUpLog.board.service;

import com.back_community.api.wakeUpLog.board.domain.dto.request.CreateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.response.CreateWakeUpResponse;

public interface WakeUpLogService {
    CreateWakeUpResponse createWakeUpLog(CreateWakeUpLogDto createWakeUpLogDto, Long userId);

}
