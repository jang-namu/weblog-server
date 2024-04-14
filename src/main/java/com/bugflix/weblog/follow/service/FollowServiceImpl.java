package com.bugflix.weblog.follow.service;

import com.bugflix.weblog.follow.domain.Follow;
import com.bugflix.weblog.follow.dto.FollowRequest;
import com.bugflix.weblog.follow.dto.FollowResponse;
import com.bugflix.weblog.follow.repository.FollowRepository;
import com.bugflix.weblog.security.domain.CustomUserDetails;
import com.bugflix.weblog.user.domain.User;
import com.bugflix.weblog.user.repository.UserRepository;
import com.mysema.commons.lang.Pair;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;


    public void addFollow(FollowRequest followRequest, UserDetails userDetails) {
        Pair<User,User> users = searchUser(followRequest,userDetails,true);

        Follow follow = Follow
                .builder()
                .follower(users.getFirst())
                .following(users.getSecond())
                .build();

        followRepository.save(follow);
    }

    public List<FollowResponse> searchFollow(UserDetails userDetails, boolean isFollower){
        User user = ((CustomUserDetails) userDetails).getUser();
        List<Follow> follows;
        ArrayList<FollowResponse> followResponses = new ArrayList<>();

        if (isFollower) {
            follows = followRepository.findByFollowing(user);
            follows.forEach(follow -> {
                User follower = follow.getFollower();
                followResponses.add(FollowResponse.of(follower,follower.getProfile()));
            });
        } else {
            follows = followRepository.findByFollower(user);
            follows.forEach(follow -> {
                User following = follow.getFollowing();
                followResponses.add(FollowResponse.of(following,following.getProfile()));
            });
        }

        return followResponses;
    }

    @Transactional
    public void deleteFollow(FollowRequest followRequest, UserDetails userDetails, boolean isFollower) {
        Pair<User,User> users = searchUser(followRequest,userDetails,isFollower);

        followRepository.deleteByFollowerAndFollowing(users.getFirst(),users.getSecond());
    }


    /***
     * User 객체 반환
     *
     * @param followRequest 검색을 요청하는 nickname
     * @param userDetails Login한 사용자 정보
     * @param isFollower Login한 사용자의 follower 여부
     * @return first : follower<br>
     *         second : following
     */
    private Pair<User,User> searchUser(FollowRequest followRequest, UserDetails userDetails, boolean isFollower) {
        User follower,following;
        if (isFollower){
            follower = ((CustomUserDetails) userDetails).getUser();;
            following = userRepository.findByNickname(followRequest.getNickname()).orElseThrow(() -> new IllegalArgumentException("invalid nickname"));
        } else {
            follower = userRepository.findByNickname(followRequest.getNickname()).orElseThrow(() -> new IllegalArgumentException("invalid nickname"));
            following = ((CustomUserDetails) userDetails).getUser();;
        }
        return new Pair<>(follower,following);
    }

}
