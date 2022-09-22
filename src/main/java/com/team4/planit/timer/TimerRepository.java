package com.team4.planit.timer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimerRepository extends JpaRepository<Timer, Long> {
    @Query(value = "select * from timer where member_id=:memberId and timer_last_date between :startDate and :endDate", nativeQuery = true)
    List<Timer> findTimerDuringPeriod(@Param("memberId") Long memberId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
