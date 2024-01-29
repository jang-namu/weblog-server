package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.Post;
import com.bugflix.weblog.profile.Profile;
import com.bugflix.weblog.tag.Tag;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Data
public class PostPreview {
    private Long postId;
    private String title;
    private String nickname;
    private List<Tag> tags;
    private Long likeCount;
    private boolean isLiked;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public PostPreview(
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
