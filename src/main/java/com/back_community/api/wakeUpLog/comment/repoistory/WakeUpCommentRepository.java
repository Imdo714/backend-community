package com.back_community.api.wakeUpLog.comment.repoistory;

import com.back_community.api.wakeUpLog.comment.domain.entity.WakeUpComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WakeUpCommentRepository extends JpaRepository<WakeUpComment, Long> {

    @EntityGraph(attributePaths = {"user"})
    Page<WakeUpComment> findByWakeUpLogWakeUpId(Long logId, Pageable pageable);

}
