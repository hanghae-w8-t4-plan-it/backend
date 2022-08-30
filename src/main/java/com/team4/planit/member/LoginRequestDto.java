package com.team4.planit.member;

import lombok.Getter;


@Getter
public class LoginRequestDto {
    private final String email;
    private final String password;

    public LoginRequestDto(String email, String password){
        this.email = email;
        this.password=password;

    }
}