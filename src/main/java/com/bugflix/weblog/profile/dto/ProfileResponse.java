package com.bugflix.weblog.profile.dto;

import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.User;
import lombok.Getter;

@Getter
public class ProfileResponse {
    private String nickname;
    private String imageUrl;
    private String email;
    private Boolean followed;

    private ProfileResponse(User user, Profile profile, Boolean followed) {
        nickname = user.getNickname();
        email = user.getEmail();
        if (profile.getImageUrl() != null) {
            this.imageUrl = profile.getImageUrl();
        }
        this.followed = followed;
    }

    public static ProfileResponse of(User user, Profile profile) {
        return new ProfileResponse(user, profile, false);
    }
    public static ProfileResponse of(User user, Profile profile, Boolean followed) {
        return new ProfileResponse(user, profile, followed);
    }
}
