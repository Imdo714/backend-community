package com.back_community.api.wakeUpLog.dao;

import com.back_community.api.user.domain.entity.User;
import com.back_community.api.user.repository.UserRepository;
import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import com.back_community.api.wakeUpLog.board.repository.WakeUpLogRepository;
import com.back_community.global.exception.handleException.MismatchException;
import com.back_community.global.exception.handleException.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WakeUpLogDao {

    private final WakeUpLogRepository wakeUpLogRepository;
    private final UserRepository userRepository;

    public WakeUpLog saveWakeUpLog(WakeUpLog wakeUpLog){
        return wakeUpLogRepository.save(wakeUpLog);
    }

    public Boolean yesterdayByUserIdAndDate(Long userId, LocalDateTime startOfYesterday, LocalDateTime startOfToday){
        return wakeUpLogRepository.yesterdayByUserIdAndDate(userId, startOfYesterday, startOfToday);
    }

    public User getUserLock(Long userId) {
        return userRepository.findByUserIdLock(userId)
                .orElseThrow(() -> new NotFoundException("락 해당 사용자가 존재하지 않습니다."));
    }

    public void validateNotWakeUpLogToday(Long userId){
        Boolean counted = wakeUpLogRepository.countTodayLogsByUserId(userId);
        if (counted) {
            throw new MismatchException("오늘은 이미 게시물을 작성했습니다.");
        }
    }

}
