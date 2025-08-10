package com.back_community.api.wakeUpLog.comment.domain.dto.response;

import com.back_community.api.common.page.PageInfo;
import com.back_community.api.wakeUpLog.comment.domain.entity.WakeUpComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
public class CommentListResponse {

    private List<Comment> wakeUpComments;
    private PageInfo pageable;

    @Getter
    @Builder
    public static class Comment {
        private Long commentId;
        private Long userId;
        private String imageUrl;
        private String userName;
        private String content;
        private LocalDateTime createDate;
    }

    public static CommentListResponse commentListBuilder(Page<WakeUpComment> comments){
        List<Comment> list = comments.getContent().stream()
                .map(res -> Comment.builder()
                        .commentId(res.getCommentId())
                        .userId(res.getUser().getUserId())
                        .imageUrl(res.getUser().getImageUrl())
                        .userName(res.getUser().getName())
                        .content(res.getComment().getContent())
                        .createDate(res.getComment().getCreateDate())
                        .build())
                .toList();

        PageInfo pageInfo = PageInfo.pageBuilder(comments);

        return CommentListResponse.builder()
                .wakeUpComments(list)
                .pageable(pageInfo)
                .build();
    };

}
