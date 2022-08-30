package com.team4.planit.member;

import lombok.Getter;


@Getter
public class LoginRequestDto {
    private final String loginId;
    private final String password;

    public LoginRequestDto(String loginId,String password){
        this.loginId=loginId;
        this.password=password;

    }
}