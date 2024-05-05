package com.bugflix.weblog.canvas.dto;

import com.bugflix.weblog.canvas.domain.CanvasSearchType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.swing.*;

@Schema(description = "Canvas 검색 요청", requiredProperties = "query")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanvasSearchRequest {
    @Schema(description = "The maximum number of books to return. Default is 10.", example = "1")
    @NotBlank
    private String query;
    @Schema(description = "검색 타입 지정", example = "TITLE, AUTHOR")
    private CanvasSearchType type;
    @Schema(description = "조회 시작 Index", example = "0")
    @Min(0)
    private Integer offset;
    @Schema(description = "한 번에 조회할 Post 수", example = "5")
    @Max(24)
    private Integer limit;

    public CanvasSearchRequest(String query, CanvasSearchType type, Integer offset, Integer limit) {
        this.query = query;
        this.type = type;
        this.offset = offset;
        this.limit = limit;
    }
}
