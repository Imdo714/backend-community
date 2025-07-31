package com.back_community.api.wakeUpLog.board.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class WakeUpListDto {

    private Long wakeUpId;
    private String userName;
    private String imageUrl;
    private String title;
    private LocalDateTime createDate;
    private Long likeCount;
    private Long commentCount;


}
