package com.bugflix.weblog.profile.dto;

import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
@Schema(description = "사용자 Profile 응답 DTO")
@Getter
public class ProfileResponse {
    @Schema(description = "nickname", example= "namu")
    private String nickname;
    @Schema(description = "사용자 profile image url", example = "http://~")
    private String imageUrl;
    @Schema(description = "사용자의 email 주소", example = "namu@inu.ac.kr")
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
