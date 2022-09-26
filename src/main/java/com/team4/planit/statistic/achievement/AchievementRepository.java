package com.team4.planit.statistic.achievement;

import com.team4.planit.member.Member;
import com.team4.planit.statistic.concentration.Concentration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

    @Query(value = "SELECT a FROM Achievement a where (a.member.memberId = :memberId) and (a.startDate between " +
            ":startDate and :endDate) and (a.period='Day')")
    List<Achievement> findAllByMemberDuringPeriod(@Param("memberId") Long memberId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "SELECT a FROM Achievement a where (a.member.memberId = :memberId) and (a.startDate like " +
            ":startDate%) and (a.period='Day')")
    List<Achievement> findAllByMemberDaily(@Param("memberId") Long memberId, @Param("startDate") String startDate);

    Optional<Achievement> findAllByMemberAndStartDateAndPeriod(Member member, String startDate, String Period);
    @Query(value = "SELECT a FROM Achievement a where (a.member.memberId = :memberId) and (a.startDate between :startDate" +
            "       and :endDate)  and (a.period = :period)")
    List<Achievement> findAllByMemberAndPeriod(@Param("memberId")Long memberId, @Param("period")String period ,
                                                 @Param("startDate")String startDate, @Param("endDate")String endDate);
    @Query(value = "select a.startDate, a.achievementRate from Achievement a\n" +
            "    where a.member.memberId = :memberId and a.achievementRate = 100 and a.startDate like :month%\n" +
            "    and a.period = 'Day'\n" +
            "    order by a.startDate")
    List<String> findAllByMemberAndStartDate(@Param("memberId") Long memberId, @Param("month") String month);
}
