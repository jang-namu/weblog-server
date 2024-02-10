package com.bugflix.weblog.profile.repository;

import com.bugflix.weblog.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProfileRepository extends JpaRepository<Profile,Long> {

    public Profile findByUserUserId(Long userId);
}
