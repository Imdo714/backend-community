package com.back_community.api.studyLog.image.domain.entity;

import com.back_community.api.common.embedded.image.Image;
import com.back_community.api.studyLog.board.domain.entity.StudyLog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "STUDY_LOG_IMG")
public class StudyLogImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "study_log_id", nullable = false)
    private StudyLog studyLog;

    @Embedded
    private Image image;

}
