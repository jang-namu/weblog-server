package com.bugflix.weblog.follow.service;

import com.bugflix.weblog.follow.domain.Follow;
import com.bugflix.weblog.follow.dto.FollowRequest;
import com.bugflix.weblog.follow.dto.FollowResponse;
import com.bugflix.weblog.follow.repository.FollowRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import com.bugflix.weblog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FollowServiceImpl {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    public void addFollow(FollowRequest followRequest, UserDetails userDetails) {
        User follower = ((CustomUserDetails) userDetails).getUser();
        User following = userRepository.findByNickname(followRequest.getNickname()).orElseThrow(()
                -> new IllegalArgumentException("invalid nickname"));
        Follow follow = new Follow(follower, following);
        followRepository.save(follow);
    }


    public List<FollowResponse> searchFollowers(UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        List<Follow> follows = followRepository.findByFollowing(user);
        return follows.stream()
                .map(Follow::getFollower)
                .map(follower -> FollowResponse.of(follower, follower.getProfile()))
                .toList();
    }

    public List<FollowResponse> searchFollwings(UserDetails userDetails) {
        User user = ((CustomUserDetails) userDetails).getUser();
        List<Follow> follows = followRepository.findByFollower(user);
        ArrayList<FollowResponse> followResponses = new ArrayList<>();

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
