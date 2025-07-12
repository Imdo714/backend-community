package com.back_community.api.studyGroup.group.domain.entity;

import com.back_community.api.studyGroup.groupRequest.domain.entity.GroupRequest;
import com.back_community.api.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "STUDY_GROUP")
public class StudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_pwd")
    private String groupPwd;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "group_public")
    private Boolean groupPublic;

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<GroupRequest> groupRequests = new ArrayList<>();

}
