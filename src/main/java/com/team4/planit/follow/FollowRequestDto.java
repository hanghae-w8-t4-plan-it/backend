package com.team4.planit.follow;

import com.team4.planit.member.Member;
import lombok.Getter;

@Getter
public class FollowRequestDto {
    private Member followingMember;
    private Member followedMember;

    public FollowRequestDto(Member followingMember, Member followedMember) {
        this.followingMember = followingMember;
        this.followedMember = followedMember;
    }
}
