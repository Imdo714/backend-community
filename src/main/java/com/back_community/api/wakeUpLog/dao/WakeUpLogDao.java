package com.back_community.api.wakeUpLog.dao;

import com.back_community.api.user.domain.entity.User;
import com.back_community.api.user.repository.UserRepository;
import com.back_community.api.wakeUpLog.board.domain.dto.request.WakeUpListDto;
import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import com.back_community.api.wakeUpLog.board.repository.WakeUpLogRepository;
import com.back_community.api.wakeUpLog.comment.domain.entity.WakeUpComment;
import com.back_community.api.wakeUpLog.comment.repoistory.WakeUpCommentRepository;
import com.back_community.api.wakeUpLog.likes.domain.entity.WakeUpLike;
import com.back_community.api.wakeUpLog.likes.repository.WakeUpLikeRepository;
import com.back_community.global.exception.handleException.MismatchException;
import com.back_community.global.exception.handleException.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class WakeUpLogDao {

    private final WakeUpLogRepository wakeUpLogRepository;
    private final UserRepository userRepository;
    private final WakeUpLikeRepository wakeUpLikeRepository;
    private final WakeUpCommentRepository wakeUpCommentRepository;

    public WakeUpLog saveWakeUpLog(WakeUpLog wakeUpLog){
        return wakeUpLogRepository.save(wakeUpLog);
    }

    public Boolean yesterdayByUserIdAndDate(Long userId, LocalDateTime startOfYesterday, LocalDateTime startOfToday){
        return wakeUpLogRepository.yesterdayByUserIdAndDate(userId, startOfYesterday, startOfToday);
    }

    public Page<WakeUpListDto> getWakeUpLogList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return wakeUpLogRepository.findWakeUpLogs(pageable);
    }

    public int getCountWakeUpLogLikes(Long logId) {
        return wakeUpLikeRepository.findWakeUpLikesCount(logId);
    }

    public void deleteWakeUpLog(Long wakeUpId) {
        wakeUpLogRepository.deleteById(wakeUpId);
    }

    public WakeUpComment saveWakeUpComment(WakeUpComment wakeUpComment){
        return wakeUpCommentRepository.save(wakeUpComment);
    }

    public Page<WakeUpComment> getCommentList(Long logId, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "comment.createDate"));
        return wakeUpCommentRepository.findByWakeUpLogWakeUpId(logId, pageable);
    }

    public void deleteWakeUpComment(Long commentId) {
        wakeUpCommentRepository.deleteById(commentId);
    }

    public WakeUpLike saveWakeUpLike(WakeUpLike wakeUpLike){
        return wakeUpLikeRepository.save(wakeUpLike);
    }

    public void deleteLike(Long userId) {
        wakeUpLikeRepository.deleteById(userId);
    }

    public WakeUpLog getWakeUpLog(Long wakeUpId){
        return wakeUpLogRepository.findById(wakeUpId)
                .orElseThrow(() -> new NotFoundException("해당 기상 게시물은 존재하지 않습니다."));
    }

    public User getUserLock(Long userId) {
        return userRepository.findByUserIdLock(userId)
                .orElseThrow(() -> new NotFoundException("락 해당 사용자가 존재하지 않습니다."));
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("해당 사용자가 존재하지 않습니다."));
    }

    public WakeUpComment getWakeUpComment(Long commentId) {
        return wakeUpCommentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("해당 기상 게시물에 존재하지 않는 댓글입니다."));
    }

    public WakeUpLike validateLikedWakeUpLog(Long userId, Long wakeUpLogId) {
        return wakeUpLikeRepository.findByUser_UserIdAndWakeUpLog_WakeUpId(userId, wakeUpLogId)
                .orElseThrow(() -> new MismatchException("애초에 좋아요 하지 않은 게시물입니다."));
    }

    public void validateNotWakeUpLogToday(Long userId){
        Boolean counted = wakeUpLogRepository.countTodayLogsByUserId(userId);
        if (counted) {
            throw new MismatchException("오늘은 이미 게시물을 작성했습니다.");
        }
    }

    public void validateNotLikedWakeUpLog(Long userId, Long wakeUpLogId){
        boolean exists = wakeUpLikeRepository.existsByUserIdAndWakeUpLogId(userId, wakeUpLogId);
        if (exists) {
            throw new MismatchException("이미 좋아요한 게시물입니다.");
        }
    }

}
