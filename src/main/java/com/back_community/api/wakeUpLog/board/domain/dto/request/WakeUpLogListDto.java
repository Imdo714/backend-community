package com.back_community.api.wakeUpLog.board.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class WakeUpLogListDto {

    Long wakeUpId;
    String userName;
    String imageUrl;
    String title;
    LocalDateTime createDate;

}
