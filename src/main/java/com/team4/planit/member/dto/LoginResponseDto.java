package com.team4.planit.member.dto;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final Long memberId;
    private final String nickname;
    private final String profileImgUrl;


    public LoginResponseDto(Long memberId, String nickname, String profileImgUrl) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
