package com.back_community.api.wakeUpLog.board.repository;

import com.back_community.api.wakeUpLog.board.domain.dto.request.WakeUpLogListDto;
import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface WakeUpLogRepository extends JpaRepository<WakeUpLog, Long> {

    @Query("SELECT COUNT(w) > 0 FROM WakeUpLog w WHERE w.user.userId = :userId AND DATE(w.board.createDate) = CURRENT_DATE")
    Boolean countTodayLogsByUserId(Long userId);

    @Query("SELECT COUNT(w) > 0 FROM WakeUpLog w WHERE w.user.userId = :userId AND w.board.createDate >= :startOfYesterday AND w.board.createDate < :startOfToday")
    Boolean yesterdayByUserIdAndDate(Long userId, LocalDateTime startOfYesterday, LocalDateTime startOfToday);

    @Query("SELECT new com.back_community.api.wakeUpLog.board.domain.dto.request.WakeUpLogListDto(w.wakeUpId, w.board.title, w.board.createDate) FROM WakeUpLog w ORDER BY w.wakeUpId DESC")
    Page<WakeUpLogListDto> findWakeUpLogs(Pageable pageable);

    @Query("SELECT w.wakeUpLog.wakeUpId FROM WakeUpLike w WHERE w.user.userId = :userId AND w.wakeUpLog.wakeUpId IN :wakeUpLogIds")
    List<Long> findLikedLogIdsByUserId(@Param("userId") Long userId, @Param("wakeUpLogIds") List<Long> wakeUpLogIds);

}
