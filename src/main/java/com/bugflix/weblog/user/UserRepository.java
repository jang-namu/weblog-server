package com.bugflix.weblog.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByPosts_PostId(Long postId);
}
