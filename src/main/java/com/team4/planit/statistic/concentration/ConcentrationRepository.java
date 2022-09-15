package com.team4.planit.statistic.concentration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConcentrationRepository extends JpaRepository<Concentration, Long> {
    @Query(value = "SELECT * FROM concentration c where (c.member_id = :memberId) and (c.concentration_start_date like :date%)", nativeQuery = true)
    List<Concentration> findAllByMemberAndStartDate(@Param("memberId") Long memberId, @Param("date") String date);

}
