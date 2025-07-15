package com.back_community.api.wakeUpLog.comment.repoistory;

import com.back_community.api.wakeUpLog.comment.domain.entity.WakeUpComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WakeUpCommentRepository extends JpaRepository<WakeUpComment, Long> {
}
