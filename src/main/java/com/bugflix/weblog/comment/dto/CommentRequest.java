package com.bugflix.weblog.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Comment 저장 요청 DTO", requiredProperties = {"content", "postId"})
public class CommentRequest {
    @Schema(description = "Comment 본문", example = "namu's 댓글")
    @NotBlank(message = "not allow blank comment")
    private String content;

    @Schema(description = "부모 Comment의 식별자", example = "1")
    private Long parentCommentId;

    @Schema(description = "Comment가 작성된 Post의 식별자", example = "0")
    @NotNull
    private Long postId;
}

