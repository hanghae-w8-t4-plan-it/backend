package com.team4.planit.timer;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team4.planit.timer.QTimer.timer;

@Repository
public class TimerRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public TimerRepositorySupport(JPAQueryFactory queryFactory) {
        super(Timer.class);
        this.queryFactory = queryFactory;
    }

    public Integer findMaxSumElapsedTime(Member member, String month) {
        return queryFactory
                .select(timer.elapsedTime.sum())
                .from(timer)
                .where(timer.member.eq(member), timer.lastDate.contains(month))
                .groupBy(timer.lastDate)
                .orderBy(timer.elapsedTime.sum().desc())
                .fetchFirst();
    }

    public List<String> findConcentrationTimeTop(Member member, String month, Integer maxSumElapsedTime) {
        return queryFactory
                .select(timer.lastDate)
                .from(timer)
                .where(timer.member.eq(member), timer.lastDate.contains(month))
                .groupBy(timer.lastDate)
                .having(timer.elapsedTime.sum().eq(maxSumElapsedTime))
                .fetch();
    }
}