package com.bugflix.weblog.profile;

import com.bugflix.weblog.profile.dto.ProfileRequest;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "profile_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Profile {

    @EmbeddedId
    private ProfileKey id;

    private String profileImageUrl;
    private String phoneNumber;
    private LocalDate birthDate;

    public Profile(Long userId) {
        this.id = new ProfileKey(userId);
    }

    public void updateId(ProfileKey id) {
        this.id = id;
    }

    public Profile(Long userId, ProfileRequest profileReqeust) {

    }
}
