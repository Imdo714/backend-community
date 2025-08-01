package com.back_community.api.wakeUpLog.board.domain.dto.response;

import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WakeUpLogDetailResponse {

    private String title;
    private String content;
    private LocalDateTime createDate;
    private int likesCount;

    public static WakeUpLogDetailResponse of (WakeUpLog wakeUpLogDetail, int likesCount){
        return WakeUpLogDetailResponse.builder()
                .title(wakeUpLogDetail.getBoard().getTitle())
                .content(wakeUpLogDetail.getBoard().getContent())
                .createDate(wakeUpLogDetail.getBoard().getCreateDate())
                .likesCount(likesCount)
                .build();
    }
}
