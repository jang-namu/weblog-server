package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.tag.domain.Tag;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostPreviewResponse {
    private Long postId;
    private String title;
    private String nickname;
    private List<Tag> tags;
    private Long likeCount;
    private boolean isLiked;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public PostPreviewResponse(
            Post post,
            List<Tag> tags,
            String nickname,
            boolean isLiked,
            LocalDateTime createdDate,
            LocalDateTime modifiedDate,
            Long likeCount) {
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.imageUrl = post.getImageUrl();

        this.tags = tags;
        this.nickname = nickname;
        this.isLiked = isLiked;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.likeCount = likeCount;
    }

}
