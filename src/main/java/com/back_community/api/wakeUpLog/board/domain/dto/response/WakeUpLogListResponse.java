package com.back_community.api.wakeUpLog.board.domain.dto.response;

import com.back_community.api.common.page.PageInfo;
import com.back_community.api.wakeUpLog.board.domain.dto.request.WakeUpLogListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class WakeUpLogListResponse {

    private List<WakeUpList > wakeUpLists;
    private PageInfo pageable;

    @Getter
    @Builder
    public static class WakeUpList  {
        Long wakeUpId;
        String title;
        LocalDateTime createDate;
        boolean isLike;
    }

    public static WakeUpLogListResponse wakeUpListPage(Page<WakeUpLogListDto> wakeUpLogList, List<Long> likedIds){
        List<WakeUpLogListResponse.WakeUpList> list = wakeUpLogList.getContent().stream()
                .map(dto -> WakeUpLogListResponse.WakeUpList.builder()
                        .wakeUpId(dto.getWakeUpId())
                        .title(dto.getTitle())
                        .createDate(dto.getCreateDate())
                        .isLike(likedIds.contains(dto.getWakeUpId()))
                        .build())
                .toList();

        PageInfo pageInfo = PageInfo.pageBuilder(wakeUpLogList);

        return WakeUpLogListResponse.builder()
                .wakeUpLists(list)
                .pageable(pageInfo)
                .build();
    }

}
