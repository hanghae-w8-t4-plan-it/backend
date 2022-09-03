package com.team4.planit.follow;

import com.team4.planit.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "followed_member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member followedMember;

    public Follow(FollowRequestDto requestDto) {
        this.member = requestDto.getFollowingMember();
        this.followedMember = requestDto.getFollowedMember();
    }
}
