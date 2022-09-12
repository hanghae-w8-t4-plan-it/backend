package com.team4.planit.timer;

import com.team4.planit.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Timer{

    @Id
    @Column(name = "timer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timerId;

    @Column(name = "timer_set_time", nullable = false)
    private Integer setTime;

    @Column(name = "timer_remain_time", nullable = false)
    private Integer remainTime;

    @Column(name = "timer_start_date")
    private String startDate;

    @Column(name = "timer_last_date")
    private String lastDate;

    @Column(name = "timer_target_date")
    private String targetDate;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Timer(Integer setTime, Integer remainTime, String startDate, String lastDate, String targetDate, Member member) {
        this.setTime = setTime;
        this.remainTime = remainTime;
        this.startDate = startDate;
        this.lastDate = lastDate;
        this.targetDate = targetDate;
        this.member = member;
    }
}
