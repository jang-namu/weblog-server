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
    @OneToOne(mappedBy = "profile")
    private User user;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;


}
