package com.bugflix.weblog.like.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class LikeStatus {
    private long likeCount;
    private boolean isLiked;

    public LikeStatus(){
        this.likeCount = 0;
        this.isLiked = false;
    }
}
