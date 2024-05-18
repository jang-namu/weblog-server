package com.bugflix.weblog.follow.service;

import com.bugflix.weblog.common.Errors;
import com.bugflix.weblog.common.exception.ResourceNotFoundException;
import com.bugflix.weblog.follow.domain.Follow;
import com.bugflix.weblog.follow.dto.FollowRequest;
import com.bugflix.weblog.follow.dto.FollowResponse;
import com.bugflix.weblog.follow.repository.FollowRepository;
import com.bugflix.weblog.notify.servoce.NotificationService;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import com.bugflix.weblog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public void addFollow(FollowRequest followRequest, UserDetails userDetails) {
        User follower = ((CustomUserDetails) userDetails).getUser();
        User following = userRepository.findByNickname(followRequest.getNickname()).orElseThrow(()
                -> new IllegalArgumentException("invalid nickname"));
        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
        notificationService.notifyFollow(following, follower);
    }


    public List<FollowResponse.MyFollowResponse> searchFollowers(UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();

        List<Follow> toFollows = followRepository.findByFollowing(user);
        List<Follow> fromFollows = followRepository.findByFollower(user);
        Set<Long> followings = fromFollows.stream().map(Follow::getFollowing).map(User::getUserId).collect(Collectors.toSet());
        return toFollows.stream()
                .map(Follow::getFollower)
                // User - Profile 1대1 매핑 (Default=EAGER)
                .map(follower -> FollowResponse.MyFollowResponse.of(follower, follower.getProfile(), followings.contains(follower.getUserId())))
                .toList();
    }

    public List<FollowResponse> searchFollwings(UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        List<Follow> follows = followRepository.findByFollower(user);

        return follows.stream()
                .map(Follow::getFollowing)
                .map(following -> FollowResponse.of(following, following.getProfile()))
                .toList();
    }

    public List<FollowResponse> searchFollowers(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new ResourceNotFoundException(Errors.USER_NOT_FOUND));
        List<Follow> follows = followRepository.findByFollowing(user);
        return follows.stream()
                .map(Follow::getFollower)
                .map(follower -> FollowResponse.of(follower, follower.getProfile()))
                .toList();
    }

    public List<FollowResponse> searchFollwings(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new ResourceNotFoundException(Errors.USER_NOT_FOUND));
        List<Follow> follows = followRepository.findByFollower(user);

        return follows.stream()
                .map(Follow::getFollowing)
                .map(following -> FollowResponse.of(following, following.getProfile()))
                .toList();
    }

    public void deleteFollower(FollowRequest followRequest, UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        User follower = userRepository.findByNickname(followRequest.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("invalid nickname"));

        followRepository.deleteByFollowerAndFollowing(follower, user);
    }

    public void deleteFollowing(FollowRequest followRequest, UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        User following = userRepository.findByNickname(followRequest.getNickname())
                .orElseThrow(() -> new IllegalArgumentException("invalid nickname"));

        followRepository.deleteByFollowerAndFollowing(user, following);

    }
}
