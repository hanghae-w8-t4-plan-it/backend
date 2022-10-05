package com.team4.planit.timer;

import com.team4.planit.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(indexes = {@Index(name = "ti_member", columnList = "member_id"), @Index(name = "ti_last_date", columnList = "timer_last_date")})
@NoArgsConstructor
public class Timer{

    @Id
    @Column(name = "timer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long timerId;

    @Column(name = "timer_set_time", nullable = false)
    private Integer setTime;

    @Column(name = "timer_elapsed_time", nullable = false)
    private Integer elapsedTime;

    @Column(name = "timer_start_date")
    private String startDate;

    @Column(name = "timer_last_hour")
    private String lastHour;

    @Column(name = "timer_last_date")
    private String lastDate;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Timer(Integer setTime, Integer elapsedTime, String startDate, String lastHour, String lastDate, Member member) {
        this.setTime = setTime;
        this.elapsedTime = elapsedTime;
        this.startDate = startDate;
        this.lastHour = lastHour;
        this.lastDate = lastDate;
        this.member = member;
    }
}
