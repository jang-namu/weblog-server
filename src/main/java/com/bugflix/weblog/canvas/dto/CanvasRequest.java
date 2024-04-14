package com.bugflix.weblog.canvas.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanvasRequest {
    private String title;
    private String key;

    public CanvasRequest(String title, String key) {
        this.title = title;
        this.key = key;
    }
}
