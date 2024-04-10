package com.bugflix.weblog.comment.dto;

import com.bugflix.weblog.comment.domain.Comment;
import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private Long parentId;
    private Long userId;
    private String nickname;
    private String profileImageUrl;
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
