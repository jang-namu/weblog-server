package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.tag.domain.Tag;
import com.bugflix.weblog.user.domain.User;
import lombok.Data;
import com.bugflix.weblog.post.domain.Post;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private List<Tag> tags;
    private String content;
    private String memo;
    private Long likeCount;
    private boolean isLike;
    private String nickname;
    private String profileImageUrl;

    public PostResponse(Post post) {
        title = post.getTitle();
        content = post.getContent();
        memo = post.getMemo();
        postId = post.getPostId();
    }

    // Todo Profile 추가
    public PostResponse(Post post, User user, List<Tag> tags) {
        this(post);
        this.tags = tags;

        nickname = user.getNickname();
        // profileImageUrl = profile.getProfileImageUrl();
    }
}
