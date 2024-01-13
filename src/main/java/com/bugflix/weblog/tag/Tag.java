package com.bugflix.weblog.tag;

import com.bugflix.weblog.postandtag.PostAndTag;
import com.bugflix.weblog.tag.dto.TagRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "tag_tb")
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Getter
    private String tagContent;

    @OneToMany(mappedBy = "tag",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private List<PostAndTag> postAndTags;

    public Tag(TagRequest tagRequest){
        tagContent = tagRequest.getTag();
    }

    public Tag(String tagContent){
        this.tagContent = tagContent;
    }
}
