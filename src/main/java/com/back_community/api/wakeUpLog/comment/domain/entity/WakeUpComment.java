package com.back_community.api.wakeUpLog.comment.domain.entity;

import com.back_community.api.common.embedded.comment.Comment;
import com.back_community.api.user.domain.entity.User;
import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "WAKE_UP_COMMENT")
public class WakeUpComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wake_up_id", nullable = false)
    private WakeUpLog wakeUpLog;

    @Embedded
    private Comment comment;

    public static WakeUpComment builderWakeUpComment(User user, WakeUpLog wakeUpLog, Comment comment){
        return WakeUpComment.builder()
                .user(user)
                .comment(comment)
                .wakeUpLog(wakeUpLog)
                .build();
    }

}
