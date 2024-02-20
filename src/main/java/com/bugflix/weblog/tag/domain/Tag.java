package com.bugflix.weblog.tag.domain;

import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "tag_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Column(name = "tag_content")
    private String tagContent;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Tag(String tagContent) {
        this.tagContent = tagContent;
    }

    public Tag(Post post, String tagContent) {
        this.post = post;
        this.tagContent = tagContent;
    }
}
