package com.bugflix.weblog.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Schema(description = "로그인, 토큰 재발급 요청 DTO", requiredProperties = {"content", "url"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostRequest {

    @Schema(description = "포스트 제목", example = "나무의 포스트 1번")
    @NotNull
    private String title;

    @Schema(description = "포스트 본문", example = "나는 오늘 밥을 먹었다. 덮밥을 먹었다.")
    @NotNull
    private String content;

    @Schema(description = "원문 스크랩", example = "일기는 하루를 마무리하는 좋은 습관이에요. " +
                                              "우리 다 같이 일기를 써보는 건 어떨까요?")
    private String memo;

    @Schema(description = "태그", example = "[\"일기\", \"갓생\"]")
    private List<String> tags;

    @Schema(description = "원문 URL", example = "https://techblog.lotteon.com/%EC%98%A4%ED%95%98%EB%82%98%EC%9D%98-%EC%A2%8C%EC%B6%A9%EC%9A%B0%EB%8F%8C-%EC%BD%94%EB%94%A9%EC%9D%B4%EC%95%BC%EA%B8%B0-30d2185df808")
    @NotNull
    private String url;

    @Schema(description = "미리보기 이미지 URL", example = "https://avatars.githubusercontent.com/u/97587573?v=4")
    private String imageUrl;

}
