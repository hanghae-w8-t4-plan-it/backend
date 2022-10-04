package com.team4.planit.statistic.concentration;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.member.Member;
import com.team4.planit.report.dto.MostConcentrationTimeResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.team4.planit.statistic.concentration.QConcentration.concentration;

@Repository
public class ConcentrationRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public ConcentrationRepositorySupport(JPAQueryFactory queryFactory) {
        super(Concentration.class);
        this.queryFactory = queryFactory;
    }

    public MostConcentrationTimeResponseDto findMostConcentrationTime(Member member, String month) {
        return queryFactory
                .select(Projections.constructor(
                        MostConcentrationTimeResponseDto.class,
                        concentration.startDate.substring(11, 14),
                        concentration.concentrationTime.sum()
                ))
                .from(concentration)
                .where(concentration.member.eq(member),
                        concentration.period.eq("Day"),
                        concentration.startDate.contains(month))
                .groupBy(concentration.startDate.substring(11, 14))
                .orderBy(concentration.concentrationTime.sum().desc())
                .fetchFirst();
    }

//    public List<ConcentrationRateResponseDto> findAllByMemberAndStartDateAndDay(Member member, String date) {
//        return queryFactory
//                .select(Projections.constructor(
//                        ConcentrationRateResponseDto.class,
//                        concentration.concentrationRate,
//                        concentration.startDate.substring(11, 14)
//                ))
//                .from(concentration)
//                .where(concentration.member.eq(member),
//                        concentration.period.eq("Day"),
//                        concentration.startDate.contains(date))
//                .fetch();
//    }
}
