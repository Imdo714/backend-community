package com.back_community.api.studyLog.studyLogTag.domain.entity;

import com.back_community.api.studyLog.board.domain.entity.StudyLog;
import com.back_community.api.studyLog.hashtag.domain.entity.Hashtag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "STUDY_LOG_TAG")
public class StudyLogTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_log_tag_id")
    private Long studyLogTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "study_log_id", nullable = false)
    private StudyLog studyLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Hashtag hashtag;

}
