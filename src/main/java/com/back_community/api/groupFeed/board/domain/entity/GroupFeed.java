package com.back_community.api.groupFeed.board.domain.entity;

import com.back_community.api.common.embedded.board.Board;
import com.back_community.api.groupFeed.comment.domain.entity.FeedComment;
import com.back_community.api.groupFeed.image.domain.entity.FeedImage;
import com.back_community.api.groupFeed.likes.domain.entity.FeedLike;
import com.back_community.api.studyGroup.group.domain.entity.StudyGroup;
import com.back_community.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "GROUP_FEED")
public class GroupFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feed_id")
    private Long feedId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private StudyGroup group;

    @Embedded
    private Board board;

    @Column(name = "feed_public")
    private Boolean feedPublic;

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<FeedImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<FeedComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<FeedLike> likes = new ArrayList<>();

}
