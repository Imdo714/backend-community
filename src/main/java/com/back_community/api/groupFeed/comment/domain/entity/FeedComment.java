package com.back_community.api.groupFeed.comment.domain.entity;

import com.back_community.api.common.embedded.comment.Comment;
import com.back_community.api.groupFeed.board.domain.entity.GroupFeed;
import com.back_community.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FEED_COMMENT")
public class FeedComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private GroupFeed feed;

    @Embedded
    private Comment comment;

}
