package com.bugflix.weblog.like.dto;

import lombok.Data;

@Data
public class LikeStatusResponse {
    private long likeCount;
    private boolean isLiked;

    public LikeStatusResponse() {
        this.likeCount = 0;
        this.isLiked = false;
    }
}
