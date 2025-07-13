package com.back_community.api.wakeUpLog.board.repository;

import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface WakeUpLogRepository extends JpaRepository<WakeUpLog, Long> {

    @Query("SELECT COUNT(w) > 0 FROM WakeUpLog w WHERE w.user.userId = :userId AND DATE(w.board.createDate) = CURRENT_DATE")
    Boolean countTodayLogsByUserId(Long userId);

    @Query("SELECT COUNT(w) > 0 FROM WakeUpLog w WHERE w.user.userId = :userId AND w.board.createDate >= :startOfYesterday AND w.board.createDate < :startOfToday")
    Boolean yesterdayByUserIdAndDate(Long userId, LocalDateTime startOfYesterday, LocalDateTime startOfToday);


}
