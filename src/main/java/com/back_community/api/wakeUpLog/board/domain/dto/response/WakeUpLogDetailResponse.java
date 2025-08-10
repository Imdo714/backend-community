package com.back_community.api.wakeUpLog.board.domain.dto.response;

import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class WakeUpLogDetailResponse {

    private String title;
    private String content;
    private String imageUrl;
    private Long writeUserId;
    private String writeUserProfile;
    private String writeUserName;
    private LocalDateTime createDate;
    private int likesCount;
    private boolean isLiked;

    public static WakeUpLogDetailResponse of (WakeUpLog wakeUpLogDetail, int likesCount, boolean isLiked){
        return WakeUpLogDetailResponse.builder()
                .title(wakeUpLogDetail.getBoard().getTitle())
                .content(wakeUpLogDetail.getBoard().getContent())
                .imageUrl(wakeUpLogDetail.getImageUrl())
                .writeUserId(wakeUpLogDetail.getUser().getUserId())
                .writeUserProfile(wakeUpLogDetail.getUser().getImageUrl())
                .writeUserName(wakeUpLogDetail.getUser().getName())
                .createDate(wakeUpLogDetail.getBoard().getCreateDate())
                .likesCount(likesCount)
                .isLiked(isLiked)
                .build();
    }
}
