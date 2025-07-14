package com.back_community.api.wakeUpLog.likes.repository;

import com.back_community.api.wakeUpLog.likes.domain.entity.WakeUpLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WakeUpLikeRepository extends JpaRepository<WakeUpLike, Long>  {

    @Query("SELECT COUNT(w) FROM WakeUpLike w WHERE w.wakeUpLog.wakeUpId = :logId")
    int findWakeUpLikesCount(Long logId);


}
