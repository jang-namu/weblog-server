package com.bugflix.weblog.post;

import com.bugflix.weblog.like.Like;
import com.bugflix.weblog.like.LikeKey;
import com.bugflix.weblog.post.dto.PostRequest;
import com.bugflix.weblog.postandtag.PostAndTag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.bugflix.weblog.page.Page;
import com.bugflix.weblog.user.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "post_tb")
@NoArgsConstructor
@Getter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    private String title;
    private String content;
    private String memo;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="page_id",nullable = false)
    private Page page;

/*
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<PostAndTag> postAndTags;
*/


    public Post(PostRequest postRequest){
        title = postRequest.getTitle();
        content = postRequest.getContent();
        memo = postRequest.getMemo();
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
