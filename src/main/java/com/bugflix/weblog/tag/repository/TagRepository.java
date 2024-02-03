package com.bugflix.weblog.tag.repository;

import com.bugflix.weblog.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    public List<Tag> findTagsByPostPostId(Long postId);

}
