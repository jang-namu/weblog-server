package com.bugflix.weblog.follow.repository;

import com.bugflix.weblog.follow.domain.Follow;
import com.bugflix.weblog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FollowRepository  extends JpaRepository<Follow, Long> {

    List<Follow> findByFollower(User follower);
    List<Follow> findByFollowing(User following);
    @Transactional
    void deleteByFollowerAndFollowing(User follower, User following);

    Boolean existsByFollowerAndFollowing(User follower, User following);
}
