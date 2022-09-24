package com.team4.planit.statistic.achievement;

import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    @Query(value = "select achivement_start_date\n" +
            "from (\n" +
            "         select rank() over (order by a.achievement_cnt desc) as achievement_count_rank,\n" +
            "                a.achievement_start_date as achivement_start_date\n" +
            "         from achievement a\n" +
            "         where a.member_id = :memberId and a.achievement_start_date like :month%\n" +
            "    ) as result\n" +
            "where achievement_count_rank = 1", nativeQuery = true)
    List<String> findAchievementCountTop(@Param("memberId") Long memberId, @Param("month") String month);
    
    @Query(value = "SELECT * FROM achievement where (member_id = :memberId) and (achievement_start_date between :startDate and :endDate) and (achievement_period='Day')", nativeQuery = true)
    List<Achievement> findAllByMemberDuringPeriod(@Param("memberId") Long memberId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    Optional<Achievement> findAllByMemberAndStartDateAndPeriod(Member member, String startDate, String Period);

    @Query(value = "select a.startDate, a.achievementRate from Achievement a\n" +
            "    where a.member.memberId = :memberId and a.achievementRate = 100 and a.startDate like :month%\n" +
            "    and a.period = 'Day'\n" +
            "    order by a.startDate")
    List<String> findAllByMemberAndStartDate(@Param("memberId") Long memberId, @Param("month") String month);
}
