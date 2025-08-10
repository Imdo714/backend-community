package com.back_community.api.wakeUpLog.comment.domain.dto.response;

import com.back_community.api.wakeUpLog.comment.domain.entity.WakeUpComment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {

    private Long commentId;
    private Long userId;
    private String imageUrl;
    private String userName;
    private String content;
    private LocalDateTime createDate;

    public static CommentResponse commentBuilder(WakeUpComment comment){
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUser().getUserId())
                .imageUrl(comment.getUser().getImageUrl())
                .userName(comment.getUser().getName())
                .content(comment.getComment().getContent())
                .createDate(comment.getComment().getCreateDate())
                .build();
    };
}
