package com.bugflix.weblog.canvas.dto;

import com.bugflix.weblog.canvas.domain.CanvasSearchType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanvasSearchRequest {
    @NotBlank
    private String query;
    private CanvasSearchType type;
    @Min(0)
    private Integer offset;
    @Max(24)
    private Integer limit;

    public CanvasSearchRequest(String query, CanvasSearchType type, Integer offset, Integer limit) {
        this.query = query;
        this.type = type;
        this.offset = offset;
        this.limit = limit;
    }
}
