package com.bugflix.weblog.user.repository;

import com.bugflix.weblog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String loginId);
    User findByPosts_PostId(Long postId);

}
