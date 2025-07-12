package com.back_community.api.studyLog.likes.domain.entity;

import com.back_community.api.studyLog.board.domain.entity.StudyLog;
import com.back_community.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "STUDY_LIKES")
public class StudyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "study_log_id", nullable = false)
    private StudyLog studyLog;

}
