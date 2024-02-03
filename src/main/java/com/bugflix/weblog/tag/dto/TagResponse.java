package com.bugflix.weblog.tag.dto;

import com.bugflix.weblog.tag.domain.Tag;
import lombok.Getter;

@Getter
public class TagResponse {
    String tagContent;

    private TagResponse(String tagContent) {
        this.tagContent = tagContent;
    }

    public static TagResponse from(Tag tag) {
        return new TagResponse(tag.getTagContent());
    }
}
