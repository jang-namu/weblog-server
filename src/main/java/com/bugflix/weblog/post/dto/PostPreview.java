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
    private boolean isLike;
    private String imageUrl;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;


    public PostPreview(Post post){
        this.postId = post.getPostId();
        this.title = post.getTitle();
        this.imageUrl = post.getImageUrl();
    }
}
