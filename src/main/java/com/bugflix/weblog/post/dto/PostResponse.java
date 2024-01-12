package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.profile.Profile;
import com.bugflix.weblog.tag.Tag;
import com.bugflix.weblog.user.User;
import com.mysql.cj.log.ProfilerEvent;
import lombok.Data;
import com.bugflix.weblog.post.Post;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PostResponse {
    private String title;
    private String content;
    private String memo;
    private ArrayList<String> tags;
    private Long likeCount;
    private boolean isLike;
    private String nickname;
    private String profileImageLink;

    public PostResponse(Post post){
        title = post.getTitle();
        content = post.getContent();
        memo = post.getMemo();
    }
    public PostResponse(Post post, List<Tag> tags, Profile profile, User user){
        this(post);
        this.tags = new ArrayList<>();
        for (Tag tag:tags){
            this.tags.add(tag.getTag());
        }

    }
}
