package com.team4.planit.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberConcentrationDto {
    private Long memberId;
    private String nickname;
    private String profileImgUrl;
    private Integer sum;

    public MemberConcentrationDto(Long memberId, String nickname, String profileImgUrl, Integer sum) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.sum = sum;
    }
}
