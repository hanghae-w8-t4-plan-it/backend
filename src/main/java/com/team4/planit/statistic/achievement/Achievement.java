package com.team4.planit.statistic.achievement;

import com.team4.planit.member.Member;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Achievement {

    @Id
    @Column(name = "achievement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long achievementId;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "achievement_period")
    private String period;

    @Column(name = "achievement_rate")
    private Float achievementRate;

    @Column(name = "achievement_cnt")
    private Integer achievementCnt;

    @Column(name = "achievement_start_date")
    private String startDate;

    @Builder
    public Achievement(Member member, String period, Float achievementRate, Integer achievementCnt, String startDate) {
        this.member = member;
        this.period = period;
        this.achievementRate = achievementRate;
        this.achievementCnt = achievementCnt;
        this.startDate = startDate;
    }
    public void update(float achievementRate,Integer achievementCnt){
        this.achievementRate = achievementRate;
        this.achievementCnt = achievementCnt;
    }
}
