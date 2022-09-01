package com.team4.planit.member;

import lombok.Getter;

@Getter
public class LoginResponseDto {
    private final String email;
    private final String nickname;
    private final String profilePhoto;


    public LoginResponseDto(String email, String nickname, String profilePhoto) {
        this.email=email;
        this.nickname = nickname;
        this.profilePhoto = profilePhoto;
    }
}
