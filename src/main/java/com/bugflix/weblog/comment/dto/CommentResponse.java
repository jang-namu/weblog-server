package com.bugflix.weblog.comment.dto;

import com.bugflix.weblog.comment.domain.Comment;
import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Comment 응답 DTO")
public class CommentResponse {
    @Schema(description = "식별자", example = "1")
    private Long commentId;
    @Schema(description = "부모 Comment 식별자", example = "0")
    private Long parentId;
    @Schema(description = "작성자의 식별자", example = "0")
    private Long userId;
    @Schema(description = "작성자의 nickname", example = "namu")
    private String nickname;
    @Schema(description = "작성자의 profile image url", example = "http://~")
    private String profileImageUrl;
    @Schema(description = "본문", example = "namu's 댓글")
    private String content;

    private CommentResponse(Comment comment, User user, Profile profile) {
        commentId = comment.getCommentId();
        if (comment.getParentComment() != null) {
            parentId = comment.getParentComment().getCommentId();
        }
        userId = user.getUserId();
        nickname = user.getNickname();
        profileImageUrl = profile.getImageUrl();
        content = comment.getContent();
    }

    public static CommentResponse of(Comment comment, User user, Profile profile) {
        return new CommentResponse(comment, user, profile);
    }
}
