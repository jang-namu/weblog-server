package com.bugflix.weblog.post.dto;

import com.bugflix.weblog.post.domain.Period;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostPopularRequest {
    private Period type;
    private Integer offset;
    private Integer limit;
}
