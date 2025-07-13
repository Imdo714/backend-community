package com.back_community.api.wakeUpLog.board.domain.dto.response;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreateWakeUpResponse {

    private Long wakeUpId;
    private int wakeUpStreak;

    public static CreateWakeUpResponse createWakeUpSuccess(Long wakeUpId, int wakeUpStreak){
        return CreateWakeUpResponse.builder()
                .wakeUpId(wakeUpId)
                .wakeUpStreak(wakeUpStreak)
                .build();
    }

}
