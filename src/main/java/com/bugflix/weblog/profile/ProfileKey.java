package com.bugflix.weblog.profile;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProfileKey implements Serializable {

    private Long userId;

    public ProfileKey(){}

    public ProfileKey(Long userId){
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileKey profileKey = (ProfileKey)o;

        return Objects.equals(userId,profileKey.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
