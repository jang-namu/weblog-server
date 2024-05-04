package com.bugflix.weblog.profile.domain;

import com.bugflix.weblog.common.BaseTimeEntity;
import com.bugflix.weblog.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity(name = "profile_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_id")
    private Long profileId;

    // todo 1. CascadeType.ALL ?
    // todo 2. targetEntity? mappedBy?
    // todo 3. 1대1 매핑 best practice?
    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    public Profile(String phoneNumber, LocalDate birthDate) {
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public Profile(String phoneNumber, LocalDate birthDate, String imageUrl) {
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.imageUrl = imageUrl;
    }

    public void changeProfileImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void update(String imageUrl, String phoneNumber) {
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
    }

    public void assignUser(User user) {
        this.user = user;
    }

}
