package com.team4.planit.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private Long memberId;
    private String nickname;
    private String profileImgUrl;
    private Boolean isKakao;

    @Builder
    public MemberResponseDto(Long memberId, String nickname, String profileImgUrl, Boolean isKakao) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.isKakao=isKakao;
    }
}
