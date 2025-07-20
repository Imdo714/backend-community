package com.back_community.api.wakeUpLog.board.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateWakeUpLogDto {

    @NotBlank(message = "기상 제목은 필수입니다.")
    private String title;
    
    @NotBlank(message = "기상 내용은 필수입니다.")
    private String content;

}
