package com.back_community.api.common.embedded.comment;

import com.back_community.api.wakeUpLog.comment.domain.dto.request.CreateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.UpdateCommentDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Comment {

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public static Comment builderComment(CreateCommentDto createCommentDto){
        return Comment.builder()
                .content(createCommentDto.getContent())
                .createDate(LocalDateTime.now())
                .build();
    }

    public void updateComment(UpdateCommentDto updateCommentDto) {
        if (updateCommentDto.getContent() != null) {
            this.content = updateCommentDto.getContent();
        }
    }
}
