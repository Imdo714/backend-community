package com.back_community.api.wakeUpLog.board.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UpdateWakeUpLogDto {

    private String title;
    private String content;
}
