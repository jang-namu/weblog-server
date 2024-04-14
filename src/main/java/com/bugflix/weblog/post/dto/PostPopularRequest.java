package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.Period;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostPopularRequest {

    @NotNull
    private Period type;

    @NotNull @PositiveOrZero
    private Integer offset;

    @NotNull @Positive
    private Integer limit;
}

