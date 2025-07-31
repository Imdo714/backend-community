package com.back_community.api.wakeUpLog.board.domain.dto.response;

import com.back_community.api.common.page.PageInfo;
import com.back_community.api.wakeUpLog.board.domain.dto.request.WakeUpListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class WakeUpLogListResponse {

    private List<WakeUpListDto> wakeUpLists;
    private PageInfo pageable;
}
