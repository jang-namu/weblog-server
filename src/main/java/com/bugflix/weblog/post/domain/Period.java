package com.bugflix.weblog.post.domain;

import lombok.Getter;

public enum Period {
    WEEKLY(7),
    MONTHLY(30),
    YEARLY(365);

    @Getter
    private final Integer value;

    Period(Integer value) {
        this.value = value;
    }
}
