package com.bugflix.weblog.profile.repository;

import com.bugflix.weblog.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProfileRepository extends JpaRepository<Profile,Long> {

    Optional<Profile> findByUserUserId(Long userId);
}
