package com.back_community.api.wakeUpLog.board.service;

import com.back_community.api.wakeUpLog.board.domain.dto.request.CreateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.request.UpdateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.response.CreateWakeUpResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogDetailResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WakeUpLogListResponse;
import com.back_community.api.wakeUpLog.board.domain.dto.response.WriteWakeUpTop3;
import org.springframework.web.multipart.MultipartFile;

public interface WakeUpLogService {
    CreateWakeUpResponse createWakeUpLog(CreateWakeUpLogDto createWakeUpLogDto, MultipartFile image, Long userId);

    WakeUpLogListResponse getWakeUpLogList(int page, int size);

    WakeUpLogDetailResponse wakeUpLogDetail(Long logId, Long userId);

    WakeUpLogDetailResponse wakeUpLogUpdate(Long logId, Long userId, UpdateWakeUpLogDto updateWakeUpLogDto);

    void wakeUpLogDelete(Long logId, Long userId);

    WriteWakeUpTop3 getWriteWakeUpRank();
}
