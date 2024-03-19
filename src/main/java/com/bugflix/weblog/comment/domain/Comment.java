package com.bugflix.weblog.comment.domain;

import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.post.domain.Post;
import com.bugflix.weblog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "comment_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> childrenComment = new ArrayList<>();

    private Comment(String content, User user, Post post) {
        this.user = user;
        this.post = post;
        this.content = content;
    }

    private Comment(String content, User user, Post post, Comment parentComment) {
        this(content, user, post);
        this.parentComment = parentComment;
    }

    public static Comment of(String content, User user, Post post) {
        return new Comment(content, user, post);
    }

    public static Comment of(String content, User user, Post post, Comment parentComment) {
        return new Comment(content, user, post, parentComment);
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
