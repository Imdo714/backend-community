package com.back_community.api.wakeUpLog.board.domain.dto.response;

import com.back_community.api.wakeUpLog.board.domain.dto.request.WakeUpTop3Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class WriteWakeUpTop3 {
    private List<WakeUpTop3Dto> writeTop3;

    public static WriteWakeUpTop3 top3Builder(List<WakeUpTop3Dto> writeWakeUpRank){
        return WriteWakeUpTop3.builder()
                .writeTop3(
                        writeWakeUpRank.stream()
                                .map(
                                    top3 -> WakeUpTop3Dto.builder()
                                            .userId(top3.getUserId())
                                            .userName(top3.getUserName())
                                            .logCount(top3.getLogCount())
                                            .build()
                        ).toList()
                ).build();
    }

}
