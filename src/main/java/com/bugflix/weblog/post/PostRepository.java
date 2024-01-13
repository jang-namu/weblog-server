package com.bugflix.weblog.post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    public List<Post> findByPageUrl(String url);


}
