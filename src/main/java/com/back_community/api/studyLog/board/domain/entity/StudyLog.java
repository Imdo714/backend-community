package com.back_community.api.studyLog.board.domain.entity;

import com.back_community.api.common.embedded.board.Board;
import com.back_community.api.studyLog.category.domain.entity.StudyCategory;
import com.back_community.api.studyLog.comment.domain.entity.StudyComment;
import com.back_community.api.studyLog.likes.domain.entity.StudyLike;
import com.back_community.api.studyLog.studyLogTag.domain.entity.StudyLogTag;
import com.back_community.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "STUDY_LOG")
public class StudyLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_log_id")
    private Long studyLogId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Embedded
    private Board board;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private StudyCategory category;

    @OneToMany(mappedBy = "studyLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<StudyLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "studyLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<StudyComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "studyLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<StudyLogTag> tags = new ArrayList<>();

    public static StudyLog builderStudyLog(User user, StudyCategory category, Board board){
        return StudyLog.builder()
                .user(user)
                .board(board)
                .category(category)
                .build();
    }
}
