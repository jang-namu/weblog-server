package com.bugflix.weblog.canvas.dto;

import io.netty.channel.ChannelHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "Canvas 요청", requiredProperties = {"title", "key"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CanvasRequest {
    @Schema(description = "Canvas 제목", example = "나무의 캔버스 1번")
    @NotEmpty
    private String title;

    @Schema(description = "S3에 업로드 된 Canvas를 찾을 수 있는 key", example = "{url 또는 리소스를 식별할 수 있는 키}")
    @NotBlank
    private String key;

    public CanvasRequest(String title, String key) {
        this.title = title;
        this.key = key;
    }
}
