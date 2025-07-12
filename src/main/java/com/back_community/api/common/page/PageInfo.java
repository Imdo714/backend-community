package com.back_community.api.common.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
public class PageInfo {
    private final long totalElements;
    private final int totalPages;
    private final int currentPage;
    private final int size;


    public static PageInfo pageBuilder(Page<?> page){
        return PageInfo.builder()
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .size(page.getSize())
                .build();
    }

}
