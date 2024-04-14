package com.bugflix.weblog.canvas.dto;

import com.bugflix.weblog.canvas.domain.Canvas;
import com.bugflix.weblog.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanvasResponse {
    private Long canvasId;
    private String title;
    private String key;
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
