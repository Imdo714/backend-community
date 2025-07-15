package com.back_community.api.wakeUpLog.likes.service;

import com.back_community.api.user.domain.entity.User;
import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import com.back_community.api.wakeUpLog.dao.WakeUpLogDao;
import com.back_community.api.wakeUpLog.likes.domain.entity.WakeUpLike;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WakeUpLikeServiceImpl implements WakeUpLikeService {

    private final WakeUpLogDao wakeUpLogDao;

    @Override
    @Transactional
    public void createLike(Long logId, Long userId) {
        wakeUpLogDao.validateNotLikedWakeUpLog(userId, logId);

        User user = wakeUpLogDao.getUser(userId);
        WakeUpLog wakeUpLog = wakeUpLogDao.getWakeUpLog(logId);

        WakeUpLike wakeUpLike = WakeUpLike.buliderWakeUpLike(user, wakeUpLog);
        WakeUpLike like = wakeUpLogDao.saveWakeUpLike(wakeUpLike);
    }

    @Override
    @Transactional
    public void deleteLike(Long logId, Long userId) {
        WakeUpLike wakeUpLike = wakeUpLogDao.validateLikedWakeUpLog(userId, logId);
        wakeUpLogDao.deleteLike(wakeUpLike.getLikeId());
    }

}
