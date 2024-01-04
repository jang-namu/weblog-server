package post.dto;

import lombok.Data;
import post.Post;

@Data
public class PostResponse {
    private String title;
    private String content;
    private Long like;
    public PostResponse(Post post){
        title = post.getTitle();
        content = post.getContent();
        like = post.getLike();
    }
}
