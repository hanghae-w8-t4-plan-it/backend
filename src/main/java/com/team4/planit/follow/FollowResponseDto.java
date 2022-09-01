package com.team4.planit.follow;

import com.team4.planit.member.Member;
import lombok.Getter;

@Getter
public class FollowResponseDto {
    private Member followingMember;
    private Member followedMember;
}
