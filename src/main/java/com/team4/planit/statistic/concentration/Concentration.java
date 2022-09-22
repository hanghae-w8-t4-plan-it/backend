package com.team4.planit.statistic.concentration;

import com.team4.planit.member.Member;
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

    @Column(name = "concentration_period")
    private String period;

    @Column(name = "concentration_rate")
    private Float concentrationRate;

    @Column(name = "concentration_time")
    private Integer concentrationTime;

    @Column(name = "concentration_start_date")
    private String startDate;

    @Builder
    public Concentration(Member member, String period, Float concentrationRate, Integer concentrationTime, String startDate) {
        this.member = member;
        this.period = period;
        this.concentrationRate = concentrationRate;
        this.concentrationTime = concentrationTime;
        this.startDate = startDate;
    }

    public void update(Member member, String period, Float concentrationRate, Integer concentrationTime, String startDate) {
        this.member = member;
        this.period = period;
        this.concentrationRate = concentrationRate;
        this.concentrationTime = concentrationTime;
        this.startDate = startDate;
    }
}
