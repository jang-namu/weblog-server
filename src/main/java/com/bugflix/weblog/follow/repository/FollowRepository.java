package com.bugflix.weblog.follow.repository;

import com.bugflix.weblog.follow.domain.Follow;
import com.bugflix.weblog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FollowRepository  extends JpaRepository<Follow, Long> {

    public List<Follow> findByFollower(User follower);
    public List<Follow> findByFollowing(User following);
    @Transactional
    public void deleteByFollowerAndFollowing(User follower, User following);

}
