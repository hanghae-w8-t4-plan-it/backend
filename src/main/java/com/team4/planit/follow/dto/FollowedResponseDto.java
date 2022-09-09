package com.team4.planit.follow.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowedResponseDto {
    private Long followedMember;
    private String nickname;
    private String profileImgUrl;

    public FollowedResponseDto(Long followedMember, String nickname, String profileImgUrl) {
        this.followedMember = followedMember;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }
}
