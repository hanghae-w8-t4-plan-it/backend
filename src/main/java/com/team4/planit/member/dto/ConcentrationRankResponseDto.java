package com.team4.planit.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcentrationRankResponseDto {
    private Long memberId;
    private String nickname;
    private String profileImgUrl;

    @Builder
    public ConcentrationRankResponseDto(Long memberId, String nickname, String profileImgUrl) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
