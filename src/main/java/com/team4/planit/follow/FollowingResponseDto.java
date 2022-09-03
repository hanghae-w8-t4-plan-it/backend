package com.team4.planit.follow;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowingResponseDto {
    private Long followingMember;
    private String nickname;
    private String profilePhoto;

    public FollowingResponseDto(Long followingMember, String nickname, String profilePhoto) {
        this.followingMember = followingMember;
        this.nickname = nickname;
        this.profilePhoto = profilePhoto;
    }
}
