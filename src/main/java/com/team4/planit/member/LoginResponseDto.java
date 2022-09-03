package com.team4.planit.member;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String email;
    private final String nickname;
    private final String profileImgUrl;


    public LoginResponseDto(String email, String nickname, String profileImgUrl) {
        this.email=email;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
