package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.SearchType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Schema(description = "Post 검색 요청 DTO", requiredProperties = {"query", "type", "offset", "limit"})
@Getter
public class PostSearchRequest {
    @NotBlank
    private String query;

    @Schema(description = "검색 Type지정 TAG/ CONTENT / TAG_AND_CONTENT", example = "TAG")
    @NotNull
    private SearchType type;

    @Schema(description = "조회 시작 인덱스", example = "10")
    @NotNull
    @PositiveOrZero
    private Integer offset;

    @Schema(description = "한 번에 조회할 Post의 수", example = "5")
    @NotNull
    @Positive
    private Integer limit;

    private PostSearchRequest(String query, SearchType type, Integer offset, Integer limit) {
        this.query = query;
        this.type = type;
        this.offset = offset;
        this.limit = limit;
    }
}
