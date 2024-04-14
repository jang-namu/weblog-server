package com.bugflix.weblog.follow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.internal.build.AllowNonPortable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequest {
    @NotBlank(message = "not allow blank nickname")
    private String nickname;
}
