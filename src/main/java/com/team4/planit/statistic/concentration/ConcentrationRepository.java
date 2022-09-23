package com.team4.planit.statistic.concentration;

import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcentrationRepository extends JpaRepository<Concentration, Long> {
    @Query(value = "SELECT * FROM concentration c where (c.member_id = :memberId) and (c.concentration_start_date like :date%)\n" +
            "       and (c.concentration_period = 'Day')", nativeQuery = true)
    List<Concentration> findAllByMemberAndStartDateAndDay(@Param("memberId") Long memberId, @Param("date") String date);
    Optional<Concentration> findConcentrationByMemberAndStartDateAndPeriod(Member member, String startDate, String Period);
}
