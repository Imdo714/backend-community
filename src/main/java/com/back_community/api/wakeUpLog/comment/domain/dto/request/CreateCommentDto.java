package com.back_community.api.wakeUpLog.comment.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateCommentDto {

    private String content;
}
