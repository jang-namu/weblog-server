package com.bugflix.weblog.profile.dto;

import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.User;
import lombok.Getter;

@Getter
public class ProfileResponse {
    private String nickname;
    private String imageUrl;
    private String email;

    private ProfileResponse(User user, Profile profile) {
        nickname = user.getNickname();
        email = user.getEmail();

        if (profile.getImageUrl() != null) {
            this.imageUrl = profile.getImageUrl();
        }
    }

    public static ProfileResponse of(User user, Profile profile) {
        return new ProfileResponse(user, profile);
    }
}
