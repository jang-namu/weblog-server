package com.bugflix.weblog.like.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LikeStatusResponse {

    @Schema(description = "좋아요 갯수", example = "0")
    private Long likeCount;

    @Schema(description = "좋아요 체크표시", example = "false")
    private Boolean isLiked;

    public LikeStatusResponse(Long likeCount, Boolean isLiked) {
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }
}
