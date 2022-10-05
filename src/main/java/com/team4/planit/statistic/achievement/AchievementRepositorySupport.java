package com.team4.planit.statistic.achievement;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team4.planit.statistic.achievement.QAchievement.achievement;

@Repository
public class AchievementRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public AchievementRepositorySupport(JPAQueryFactory queryFactory) {
        super(Achievement.class);
        this.queryFactory = queryFactory;
    }

    public Integer findMaxAchievementCount(Member member, String month) {
        return queryFactory
                .select(achievement.achievementCnt)
                .from(achievement)
                .where(achievement.member.eq(member), achievement.startDate.contains(month))
                .orderBy(achievement.achievementCnt.desc())
                .fetchFirst();
    }

    public List<String> findAchievementCountTop(Member member, String month, Integer maxAchievementCount) {
        return queryFactory
                .select(achievement.startDate)
                .from(achievement)
                .where(achievement.member.eq(member), achievement.startDate.contains(month), achievement.achievementCnt.eq(maxAchievementCount), achievement.achievementCnt.gt(0))
                .limit(3)
                .fetch();
    }
}