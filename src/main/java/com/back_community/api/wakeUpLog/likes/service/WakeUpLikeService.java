package com.back_community.api.wakeUpLog.likes.service;

public interface WakeUpLikeService {

    void createLike(Long logId, Long userId);

    void deleteLike(Long logId, Long userId);
}
