package com.team4.planit.timer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimerRepository extends JpaRepository<Timer, Long> {
    @Query(value = "select * from timer where member_id=:memberId and timer_last_date between :startDate and :endDate", nativeQuery = true)
    List<Timer> findTimerDuringPeriod(@Param("memberId") Long memberId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query(value = "select timer_last_date\n" +
            "from (\n" +
            "         select t.timer_last_date as timer_last_date,\n" +
            "                rank() over (order by sum(t.timer_elapsed_time) desc) as total_concentration_time_rank\n" +
            "         from timer t\n" +
            "         where t.member_id = :memberId and t.timer_last_date like :month%\n" +
            "         group by t.timer_last_date\n" +
            "    ) as result\n" +
            "where total_concentration_time_rank = 1", nativeQuery = true)
    List<String> findConcentrationTimeTop(@Param("memberId") Long memberId, @Param("month") String month);
}
