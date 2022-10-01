package com.team4.planit.member.socialLogin;

import com.team4.planit.global.jwt.TokenDto;
import com.team4.planit.member.Member;
import lombok.Getter;

@Getter
public class kakaoResponseDto {
    TokenDto tokenDto;
    Member member;

    public kakaoResponseDto(TokenDto tokenDto, Member member) {
        this.tokenDto = tokenDto;
        this.member = member;
    }
}
