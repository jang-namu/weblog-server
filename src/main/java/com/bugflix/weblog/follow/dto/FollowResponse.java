package com.bugflix.weblog.follow.dto;

import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowResponse {
    private String nickname;
    private String profileImageUrl;

    private FollowResponse(User user, Profile profile) {
        this.nickname = user.getNickname();
        this.profileImageUrl = profile.getImageUrl();
    }

    public static FollowResponse of(User user, Profile profile) {
        return new FollowResponse(user,profile);
    }
}
