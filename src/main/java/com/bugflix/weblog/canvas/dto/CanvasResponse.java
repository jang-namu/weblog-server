package com.bugflix.weblog.canvas.dto;

import com.bugflix.weblog.canvas.domain.Canvas;
import com.bugflix.weblog.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "반환되는 Canvas의 정보")
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanvasResponse {

    @Schema(description = "Canvas Identifier", example = "1")
    private Long canvasId;
    @Schema(description = "Canvas 제목", example = "나무의 Canvas")
    private String title;
    @Schema(description = "S3에 업로드된 Canvas를 찾을 수 있는 key", example = "{url 또는 리소스를 식별할 수 있는 키}")
    private String key;
    @Schema(description = "발간인 이름", example = "나무")
    private String nickname;

    @Builder
    private CanvasResponse(Long canvasId, String title, String key, String nickname) {
        this.canvasId = canvasId;
        this.title = title;
        this.key = key;
        this.nickname = nickname;
    }

    public static CanvasResponse of(Canvas canvas, User user) {
        return CanvasResponse.builder()
                .canvasId(canvas.getCanvasId())
                .title(canvas.getTitle())
                .key(canvas.getKey())
                .nickname(user.getNickname())
                .build();
    }
}
