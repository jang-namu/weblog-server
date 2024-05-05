package com.bugflix.weblog.like.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "특정 Post의 좋아요 상태 응답 DTO")
@Getter
public class LikeStatusResponse {

    @Schema(description = "좋아요 갯수", example = "0")
    private Long likeCount;

    @Schema(description = "현재 Login한 사용자의 좋아요 여부", example = "false")
    private Boolean isLiked;

    public LikeStatusResponse(Long likeCount, Boolean isLiked) {
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }
}
