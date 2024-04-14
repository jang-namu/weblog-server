package com.bugflix.weblog.canvas.dto;

import com.bugflix.weblog.post.domain.SearchType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanvasSearchRequest {
    private String query;
    private SearchType type;
    private Integer offset;
    private Integer limit;

    public CanvasSearchRequest(String query, SearchType type, Integer offset, Integer limit) {
        this.query = query;
        this.type = type;
        this.offset = offset;
        this.limit = limit;
    }
}
