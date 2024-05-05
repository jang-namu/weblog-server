package com.bugflix.weblog.follow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Follow 요청 DTO", requiredProperties = "nickname")
public class FollowRequest {
    @NotBlank(message = "not allow blank nickname")
    @Schema(description = "Follow할 사용자의 nickname", example = "jo")
    private String nickname;
}
