package com.back_community.api.wakeUpLog.likes.domain.entity;

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
@Table(name = "WAKE_UP_LIKES")
public class WakeUpLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wake_up_id", nullable = false)
    private WakeUpLog wakeUpLog;

    public static WakeUpLike buliderWakeUpLike(User user, WakeUpLog wakeUpLog){
        return WakeUpLike.builder()
                .user(user)
                .wakeUpLog(wakeUpLog)
                .build();
    }

}
