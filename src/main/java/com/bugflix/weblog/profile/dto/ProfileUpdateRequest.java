package com.bugflix.weblog.profile.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "프로필 수정 요청",
        requiredProperties = {"nickname", "imageUrl", "phoneNumber"})
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileUpdateRequest {

    @Schema(description = "별명", example = "namu")
    @Size(min = 2, max = 25)
    @NotNull
    private String nickname;

    @NotNull
    @Schema(description = "프로필 이미지")
    private String imageUrl;

    @NotNull
    @Schema(description = "전화번호", example = "01012341234")
    private String phoneNumber;

}
