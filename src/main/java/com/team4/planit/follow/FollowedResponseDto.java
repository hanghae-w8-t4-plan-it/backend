package com.team4.planit.follow;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowedResponseDto {
    private Long followedMember;
    private String nickname;
    private String profilePhoto;

    public FollowedResponseDto(Long followedMember, String nickname, String profilePhoto) {
        this.followedMember = followedMember;
        this.nickname = nickname;
        this.profilePhoto = profilePhoto;
    }
}
