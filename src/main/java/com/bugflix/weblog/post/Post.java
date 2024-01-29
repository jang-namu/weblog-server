package com.bugflix.weblog.post;

import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.post.dto.PostRequest;
import com.bugflix.weblog.tag.Tag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.bugflix.weblog.page.Page;
import com.bugflix.weblog.user.User;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "post_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long postId;

    private String title;
    private String content;
    private String memo;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="page_id",nullable = false)
    private Page page;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Tag> tags = new ArrayList<>();


    public Post(PostRequest postRequest){
        title = postRequest.getTitle();
        content = postRequest.getContent();
        memo = postRequest.getMemo();
        imageUrl = postRequest.getUrl();
    }

    public Post(PostRequest postRequest, User user, Page page){
        this(postRequest);
        this.user = user;
        this.page = page;
    }

    public void updateTitle(String title){
        this.title = title;
    }
    public void updateContent(String content){
        this.content = content;
    }
    public void updateMemo(String memo){
        this.memo = memo;
    }
}
