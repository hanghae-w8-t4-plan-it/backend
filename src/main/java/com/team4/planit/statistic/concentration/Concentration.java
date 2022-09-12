package com.team4.planit.statistic.concentration;

import com.team4.planit.member.Member;
import com.team4.planit.statistic.StatisticPeriodCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Concentration {

    @Id
    @Column(name = "concentration_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long concentrationId;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "conncentration_period")
    private StatisticPeriodCode period;

    @Column(name = "conncentration_rate")
    private Float concentrationRate;

    @Column(name = "conncentration_time")
    private Integer concentrationTime;

    @Column(name = "conncentration_start_date")
    private String startDate;

    @Builder
    public Concentration(Member member, StatisticPeriodCode period, Float concentrationRate, Integer concentrationTime, String startDate) {
        this.member = member;
        this.period = period;
        this.concentrationRate = concentrationRate;
        this.concentrationTime = concentrationTime;
        this.startDate = startDate;
    }
}
