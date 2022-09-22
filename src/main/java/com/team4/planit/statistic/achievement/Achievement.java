package com.team4.planit.statistic.achievement;

import com.team4.planit.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
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

    @Column(name = "achievement_total_todo_cnt")
    private Integer todoCnt;

    @Column(name = "achievement_start_date")
    private String startDate;

    @Builder
    public Achievement(Member member, String period, Float achievementRate, Integer achievementCnt, String startDate, Integer todoCnt) {
        this.member = member;
        this.period = period;
        this.achievementRate = achievementRate;
        this.achievementCnt = achievementCnt;
        this.startDate = startDate;
        this.todoCnt = todoCnt;
    }

    public void update(float achievementRate, Integer achievementCnt, Integer todoCnt) {
        this.achievementRate = achievementRate;
        this.achievementCnt = achievementCnt;
        this.todoCnt = todoCnt;
    }
}
