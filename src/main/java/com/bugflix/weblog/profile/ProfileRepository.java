package com.bugflix.weblog.profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile,Long> {

    public Profile findById_UserId(Long userId);
}
