package com.bugflix.weblog.postandtag;

import com.bugflix.weblog.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostAndTagRepository extends JpaRepository<PostAndTag,Long> {
    List<PostAndTag> findPostAndTagByPostPageUrl(String url);

}
