package com.bugflix.weblog.comment.dto;

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
public class CommentRequest {
    @NotBlank(message = "not allow blank comment")
    private String content;
    private Long parentCommentId;
    @NotNull
    private Long postId;
}

