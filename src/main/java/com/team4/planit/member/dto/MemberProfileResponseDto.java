package com.team4.planit.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberProfileResponseDto {
    private Long memberId;
    private String nickname;
    private String profileImgUrl;
    private Long followerCnt;
    private Long followingCnt;

    @Builder
    public MemberProfileResponseDto(Long memberId, String nickname, String profileImgUrl, Long followerCnt, Long followingCnt) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.followerCnt = followerCnt;
        this.followingCnt = followingCnt;
    }
}
