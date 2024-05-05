package com.bugflix.weblog.follow.dto;

import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Schema(description = "Follower 혹은 Following 중인 사용자의 정보 DTO")
public class FollowResponse {
    @Schema(description = "nickname", example = "namu")
    private String nickname;
    @Schema(description = "Follower 혹은 Following 중인 사용자의 profile image url", example = "http://~")
    private String profileImageUrl;

    @Getter
    @NoArgsConstructor
    public static class MyFollowResponse extends FollowResponse {
        @Schema(description = "팔로잉 상태", example = "false")
        private Boolean followed;

        private MyFollowResponse(User user, Profile profile, Boolean followed) {
            super(user.getNickname(), profile.getImageUrl());
            this.followed = followed;
        }

        public static MyFollowResponse of(User user, Profile profile, Boolean followed) {
            return new MyFollowResponse(user, profile, followed);
        }
    }

    private FollowResponse(String nickname, String profileImageUrl) {
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    private FollowResponse(User user, Profile profile) {
        this.nickname = user.getNickname();
        this.profileImageUrl = profile.getImageUrl();
    }

    public static FollowResponse of(User user, Profile profile) {
        return new FollowResponse(user,profile);
    }
}
