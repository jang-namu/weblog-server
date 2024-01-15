package com.bugflix.weblog.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostRequest {
    private String title;
    private String content;
    private String memo;
    private List<String> tags;
    private String url;
    private String imageUrl;

}
