package com.back_community.api.wakeUpLog.comment.service;

import com.back_community.api.wakeUpLog.comment.domain.dto.request.CreateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.request.UpdateCommentDto;
import com.back_community.api.wakeUpLog.comment.domain.dto.response.CommentListResponse;

public interface WakeUpCommentService {

    void createComment(CreateCommentDto createCommentDto, Long userId, Long logId);

    CommentListResponse getCommentList(Long logId, int page, int size);

    void getCommentUpdate(Long userId, Long logId, Long commentId, UpdateCommentDto updateCommentDto);

    void getCommentDelete(Long logId, Long commentId, Long userId);
}
