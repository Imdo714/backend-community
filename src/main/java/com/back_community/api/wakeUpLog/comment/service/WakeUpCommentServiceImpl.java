package com.back_community.api.wakeUpLog.comment.service;

import com.back_community.api.common.embedded.comment.Comment;
import com.back_community.api.user.domain.entity.User;
import com.back_community.api.wakeUpLog.board.domain.entity.WakeUpLog;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.CreateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.response.CommentListResponse;
import com.back_community.api.wakeUpLog.comment.domain.entity.WakeUpComment;
import com.back_community.api.wakeUpLog.dao.WakeUpLogDao;
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
    public void createComment(CreateCommentDto createCommentDto, Long userId, Long logId) {
        User user = wakeUpLogDao.getUser(userId);
        WakeUpLog wakeUpLog = wakeUpLogDao.getWakeUpLog(logId);

        Comment comment = Comment.builderComment(createCommentDto);
        WakeUpComment wakeUpComment = WakeUpComment.builderWakeUpComment(user, wakeUpLog, comment);

        WakeUpComment saved = wakeUpLogDao.saveWakeUpComment(wakeUpComment);
    }

    @Override
    public CommentListResponse getCommentList(Long logId, int page, int size) {
        Page<WakeUpComment> commentList = wakeUpLogDao.getCommentList(logId, page, size);
        return CommentListResponse.commentListBuilder(commentList);
    }

}
