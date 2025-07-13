package com.back_community.api.user.domain.entity;

import com.back_community.api.studyGroup.group.domain.entity.StudyGroup;
import com.back_community.api.studyGroup.groupRequest.domain.entity.GroupRequest;
import com.back_community.api.studyLog.board.domain.entity.StudyLog;
import com.back_community.api.user.domain.dto.request.JoinDto;
import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Getter
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private StudyGroup studyGroup;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "name")
    private String name;

    @Column(name = "user_class")
    private String userClass;

    @Column(name = "user_target")
    private String userTarget;

    @Column(name = "group_leader")
    private Boolean leader;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "wake_up_streak")
    private int wakeUpStreak;

    @Column(name = "study_streak")
    private int studyStreak;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<WakeUpLog> wakeUpLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<StudyLog> studyLogs = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<GroupRequest> groupRequests = new ArrayList<>();

    public static User createUserBuilder(JoinDto joinDto, String encodedPassword){
        return User.builder()
                .email(joinDto.getEmail())
                .password(encodedPassword)
                .name(joinDto.getName())
                .userClass(joinDto.getUserClass())
                .userTarget(joinDto.getUserTarget())
                .createDate(LocalDate.now())
                .build();
    }

    public boolean isPasswordMatch(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.password);
    }

    public void wakeUpStreakUpdate(Boolean wroteYesterday){
        if(wroteYesterday){
            this.wakeUpStreak += 1;
        } else {
            this.wakeUpStreak = 1;
        }
    }


}
