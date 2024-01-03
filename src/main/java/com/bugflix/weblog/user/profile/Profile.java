package com.bugflix.weblog.user.profile;

import com.bugflix.weblog.user.User;
import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Profile {

    @Id
    // CascadeTyoe.ALL ?
    // targetEntity? mappedBy?
    // 1대1 매핑 best practice?
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Long userId;

    private String image;

    private String phoneNumber;

    private Date birthDate;


}
