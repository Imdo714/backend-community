package com.back_community.api.common.embedded.board;

import com.back_community.api.wakeUpLog.board.domain.dto.request.CreateWakeUpLogDto;
import com.back_community.api.wakeUpLog.board.domain.dto.request.UpdateWakeUpLogDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class Board {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public static Board builderBoard(String title, String content){
        return Board.builder()
                .title(title)
                .content(content)
                .createDate(LocalDateTime.now())
                .build();
    }

    public void updateBoard(UpdateWakeUpLogDto updateWakeUpLogDto) {
        if (updateWakeUpLogDto.getTitle() != null) {
            this.title = updateWakeUpLogDto.getTitle();
        }

        if (updateWakeUpLogDto.getContent() != null) {
            this.content = updateWakeUpLogDto.getContent();
        }
    }
}
