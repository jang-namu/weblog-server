package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.SearchType;
import lombok.Getter;

@Getter
public class PostSearchRequest {
    private String query;
    private SearchType type;
    private Integer offset;
    private Integer limit;

    private PostSearchRequest(String query, SearchType type, Integer offset, Integer limit) {
        this.query = query;
        this.type = type;
        this.offset = offset;
        this.limit = limit;
    }
}
