package com.bugflix.weblog.post.dto;

import lombok.Data;
import com.bugflix.weblog.post.Post;

@Data
public class PostResponse {
    private String title;
    private String content;
    private String memo;

    public PostResponse(Post post){
        title = post.getTitle();
        content = post.getContent();
        memo = post.getMemo();
    }
}
