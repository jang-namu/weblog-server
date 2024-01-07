package com.bugflix.weblog.post;

import com.bugflix.weblog.post.dto.PostRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.bugflix.weblog.page.Page;
import com.bugflix.weblog.user.User;

import java.util.Date;

@Entity
@NoArgsConstructor
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String title;
    @Getter
    private String content;
    @Getter
    private String memo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="page_id",nullable = false)
    private Page page;

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
