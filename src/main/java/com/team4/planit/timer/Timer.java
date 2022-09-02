package com.team4.planit.timer;

import com.team4.planit.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Timer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String setTime;

    @Column(nullable = false)
    private String remainTime;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public Timer(String setTime, String remainTime, Member member) {
        this.setTime = setTime;
        this.remainTime = remainTime;
        this.member = member;
    }
}
