package com.bugflix.weblog.post.domain;

import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.post.dto.PostRequest;
import com.bugflix.weblog.tag.domain.Tag;
import com.bugflix.weblog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.bugflix.weblog.page.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "post_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    private String title;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(columnDefinition = "LONGTEXT")
    private String memo;

    @Column(name = "image_url", columnDefinition = "LONGTEXT")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "page_id", nullable = false)
    private Page page;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();

    @Column(name = "like_count")
    private Long likeCount = 0L;

    public Post(PostRequest postRequest) {
        title = postRequest.getTitle();
        content = postRequest.getContent();
        memo = postRequest.getMemo();
        imageUrl = postRequest.getUrl();
    }

    public Post(PostRequest postRequest, User user, Page page) {
        this(postRequest);
        this.user = user;
        this.page = page;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void setLike() {
        this.likeCount += 1;
    }

    public void setUnLike() {
        this.likeCount -= 1;
    }
}
