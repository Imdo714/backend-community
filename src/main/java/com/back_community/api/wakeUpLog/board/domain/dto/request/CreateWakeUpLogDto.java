package com.back_community.api.wakeUpLog.board.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWakeUpLogDto {

    private String title;
    private String content;

}
