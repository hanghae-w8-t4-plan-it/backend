package com.team4.planit.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String nickname;
    private String profileImgUrl;

    @Builder
    public MemberResponseDto(Long id, String nickname, String profileImgUrl) {
        this.id = id;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
