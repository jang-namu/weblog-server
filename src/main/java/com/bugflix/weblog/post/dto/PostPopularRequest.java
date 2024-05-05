package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.Period;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(description = "인기 Post 조회 요청 DTO", requiredProperties = {"type", "offset", "limit"})
@Getter
@AllArgsConstructor
public class PostPopularRequest {

    @Schema(description = "WEEKLEY/MONTHLY/YEARLY 중 어떤 방식으로 조회할 것인지 선택", example = "WEEKLEY")
    @NotNull
    private Period type;

    @Schema(description = "조회 시작 인덱스", example = "10")
    @NotNull
    @PositiveOrZero
    private Integer offset;

    @Schema(description = "한 번에 조회할 Post 수", example = "5")
    @NotNull
    @Positive
    private Integer limit;
}

