package com.team4.planit.follow;

import com.team4.planit.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class FollowList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followListId;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "follow_list_follow_cnt")
    private Long followCnt;

    @Column(name = "follow_list_following_cnt")
    private Long followingCnt;

    public FollowList(Member member, Long followCnt, Long followingCnt) {
        this.member = member;
        this.followCnt = followCnt;
        this.followingCnt = followingCnt;
    }
}
