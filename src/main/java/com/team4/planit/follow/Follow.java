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
    private Long id;

    @JoinColumn(name = "following_member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member followingMember;

    @JoinColumn(name = "followed_member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member followedMember;

    public Follow(FollowRequestDto requestDto) {
        this.followingMember = requestDto.getFollowingMember();
        this.followedMember = requestDto.getFollowedMember();
    }
}
