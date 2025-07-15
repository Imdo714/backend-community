package com.back_community.api.wakeUpLog.comment.service;

import com.back_community.api.wakeUpLog.comment.domain.dto.request.CreateCommentDto;

public interface WakeUpCommentService {

    void createComment(CreateCommentDto createCommentDto, Long userId, Long logId);


}
