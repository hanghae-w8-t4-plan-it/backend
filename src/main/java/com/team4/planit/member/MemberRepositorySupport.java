package com.team4.planit.member;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.member.dto.MemberConcentrationDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.team4.planit.member.QMember.member;
import static com.team4.planit.statistic.achievement.QAchievement.achievement;
import static com.team4.planit.statistic.concentration.QConcentration.concentration;

@Repository
public class MemberRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public MemberRepositorySupport(JPAQueryFactory queryFactory) {
        super(Member.class);
        this.queryFactory = queryFactory;
    }

    public List<Member> findAchievementMember(LocalDate date) {
        return queryFactory
                .selectFrom(member)
                .innerJoin(achievement)
                .on(member.eq(achievement.member))
                .where(achievement.startDate.contains(String.valueOf(date)), achievement.period.eq("Day"))
                .orderBy(achievement.achievementCnt.desc())
                .limit(3)
                .fetch();
    }

    public List<MemberConcentrationDto> findConcentrationMember(LocalDate date) {
        return queryFactory
                .select(Projections.constructor(
                        MemberConcentrationDto.class,
                        member.memberId,
                        member.nickname,
                        member.profileImgUrl,
                        concentration.concentrationTime.sum()))
                .from(member)
                .innerJoin(concentration)
                .on(member.eq(concentration.member))
                .where(concentration.startDate.contains(String.valueOf(date)), concentration.period.eq("Day"))
                .groupBy(concentration.member)
                .orderBy(concentration.concentrationTime.sum().desc())
                .limit(3)
                .fetch();
    }
}
