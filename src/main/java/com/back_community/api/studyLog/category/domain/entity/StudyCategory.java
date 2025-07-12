package com.back_community.api.studyLog.category.domain.entity;

import com.back_community.api.studyLog.category.domain.common.StudyLogCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "STUDY_CATEGORY")
public class StudyCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name")
    private StudyLogCategory studyLogCategoryName;

}
