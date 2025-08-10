package com.back_community.api.wakeUpLog.comment.service;

import com.back_community.api.common.embedded.comment.Comment;
import com.back_community.api.user.domain.entity.User;
import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.CreateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.UpdateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.response.CommentListResponse;
import com.back_community.api.wakeUpLog.comment.domain.dto.response.CommentResponse;
import com.back_community.api.wakeUpLog.comment.domain.entity.WakeUpComment;
import com.back_community.api.wakeUpLog.dao.WakeUpLogDao;
import com.back_community.global.exception.handleException.MismatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WakeUpCommentServiceImpl implements WakeUpCommentService {

    private final WakeUpLogDao wakeUpLogDao;

    @Override
    @Transactional
    public CommentResponse createComment(CreateCommentDto createCommentDto, Long userId, Long logId) {
        User user = wakeUpLogDao.getUser(userId);
        WakeUpLog wakeUpLog = wakeUpLogDao.getWakeUpLog(logId);

        Comment comment = Comment.builderComment(createCommentDto);
        WakeUpComment wakeUpComment = WakeUpComment.builderWakeUpComment(user, wakeUpLog, comment);

        WakeUpComment saved = wakeUpLogDao.saveWakeUpComment(wakeUpComment);

//        CommentListResponse.Comment commented = CommentListResponse.commentBuilder(saved);
        return CommentResponse.commentBuilder(saved);
    }

    @Override
    public CommentListResponse getCommentList(Long logId, int page, int size) {
        Page<WakeUpComment> commentList = wakeUpLogDao.getCommentList(logId, page, size);
        return CommentListResponse.commentListBuilder(commentList);
    }

    @Override
    @Transactional
    public void getCommentUpdate(Long userId, Long logId, Long commentId, UpdateCommentDto updateCommentDto) {
        WakeUpComment wakeUpComment = validateWakeUpCommentUserIsOwner(userId, commentId);
        wakeUpComment.getComment().updateComment(updateCommentDto);
    }

    @Override
    @Transactional
    public void getCommentDelete(Long logId, Long commentId, Long userId) {
        WakeUpComment wakeUpComment = validateWakeUpCommentUserIsOwner(userId, commentId);
        wakeUpLogDao.deleteWakeUpComment(wakeUpComment.getCommentId());
    }

    private WakeUpComment validateWakeUpCommentUserIsOwner(Long userId, Long commentId) {
        WakeUpComment comment = wakeUpLogDao.getWakeUpComment(commentId);

        if (!userId.equals(comment.getUser().getUserId())) {
            throw new MismatchException("댓글 작성자가 다릅니다!");
        }

        return comment;
    }

}
