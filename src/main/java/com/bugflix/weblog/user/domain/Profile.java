package com.bugflix.weblog.user.domain;

import com.bugflix.weblog.common.BaseTimeEntity;
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
    private Long profile_id;

    // CascadeTyoe.ALL ?
    // targetEntity? mappedBy?
    // 1대1 매핑 best practice?
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

    public void assignUser(User user) {
        this.user = user;
    }

    public void changeImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
