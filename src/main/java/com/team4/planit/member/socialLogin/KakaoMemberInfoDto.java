package com.team4.planit.member.socialLogin;


import lombok.Getter;

@Getter
public class KakaoMemberInfoDto {
    private final String nickname;
    private final String email;
    private final String profilePhoto;
    private final Long id;

    public KakaoMemberInfoDto(String nickname, String email, String profilePhoto, Long id){
        this.nickname=nickname;
        this.email=email;
        this.profilePhoto=profilePhoto;
        this.id=id;

    }
}
