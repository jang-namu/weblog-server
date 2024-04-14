package com.bugflix.weblog.canvas.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanvasResponse {
    private Long canvasId;
    private String title;
    private String key;
    private String nickname;
}
