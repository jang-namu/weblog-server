package com.bugflix.weblog.security.oauth.dto.response;

import com.bugflix.weblog.profile.domain.Profile;
import com.bugflix.weblog.user.domain.Authority;
import com.bugflix.weblog.user.domain.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

import java.util.Collections;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GoogleOAuthMemberResponse {
    private String familyName;
    private String sub;
    private String picture;
    private String locale;
    private Boolean emailVerified;
    private String givenName;
    private String email;
    private String name;

    public User toUser() {
        User user = User.builder()
                .email(email)
                .password("none")
                .nickname(name)
                .build();
        // todo: Role과 Profile을 builder 내에서 넣어주면 Authority, Profile 엔티티가 저장이 안됨 -> 우선 따로 분리하여 해결.
        user.assignRoles(Collections.singletonList(new Authority("ROLE_USER")));
        Profile profile = new Profile("", null, picture);
        profile.assignUser(user);
        user.assignProfile(profile);
        return user;
    }
}
