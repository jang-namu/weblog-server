package com.bugflix.weblog.tag;

import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.post.Post;
import com.bugflix.weblog.tag.dto.TagRequest;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "tag_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @Getter
    private String tagContent;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Tag(String tagContent){
        this.tagContent = tagContent;
    }

    public Tag(Post post,String tagContent){
        this.post = post;
        this.tagContent = tagContent;
    }
}
