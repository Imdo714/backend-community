package com.back_community.api.wakeUpLog.board.domain.entity;

import com.back_community.api.common.embedded.board.Board;
import com.back_community.api.user.domain.entity.User;
import com.back_community.api.wakeUpLog.comment.domain.entity.WakeUpComment;
import com.back_community.api.wakeUpLog.likes.domain.entity.WakeUpLike;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"comments", "likes"})
@Entity
@Table(name = "WAKE_UP_LOG")
public class WakeUpLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wake_up_id")
    private Long wakeUpId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Embedded
    private Board board;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "wakeUpLog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WakeUpComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "wakeUpLog", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WakeUpLike> likes = new ArrayList<>();

    public static WakeUpLog builderWakeUpLog(User user, Board board){
        return WakeUpLog.builder()
                .user(user)
                .board(board)
                .build();
    }

}
