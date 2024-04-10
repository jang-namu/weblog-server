package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.SearchType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;

@Getter
public class PostSearchRequest {
    @NotBlank
    private String query;
    @NotNull
    private SearchType type;
    @NotNull
    @PositiveOrZero
    private Integer offset;
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
