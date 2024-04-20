package com.bugflix.weblog.canvas.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanvasRequest {
    @NotEmpty
    private String title;
    @NotBlank
    private String key;

    public CanvasRequest(String title, String key) {
        this.title = title;
        this.key = key;
    }
}
