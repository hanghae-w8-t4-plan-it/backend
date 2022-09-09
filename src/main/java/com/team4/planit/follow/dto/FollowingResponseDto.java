package com.team4.planit.follow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowingResponseDto {
    private Long followingMember;
    private String nickname;
    private String profileImgUrl;

    public FollowingResponseDto(Long followingMember, String nickname, String profileImgUrl) {
        this.followingMember = followingMember;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
