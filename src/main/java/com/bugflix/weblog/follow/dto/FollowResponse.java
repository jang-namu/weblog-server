package com.bugflix.weblog.follow.dto;

import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Follower 혹은 Following 중인 사용자의 정보 DTO")
public class FollowResponse {
    @Schema(description = "nickname", example = "namu")
    private String nickname;
    @Schema(description = "Follower 혹은 Following 중인 사용자의 profile image url", example = "http://~")
    private String profileImageUrl;

    private FollowResponse(User user, Profile profile) {
        this.nickname = user.getNickname();
        this.profileImageUrl = profile.getImageUrl();
    }

    public static FollowResponse of(User user, Profile profile) {
        return new FollowResponse(user,profile);
    }
}
