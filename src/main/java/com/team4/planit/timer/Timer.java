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

    @Column(name = "timer_date")
    private String date;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Timer(Integer setTime, Integer remainTime, String date, Member member) {
        this.setTime = setTime;
        this.remainTime = remainTime;
        this.date = date;
        this.member = member;
    }
}
