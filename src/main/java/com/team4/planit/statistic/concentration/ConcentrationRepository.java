package com.team4.planit.statistic.concentration;

import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcentrationRepository extends JpaRepository<Concentration, Long> {
    @Query(value = "SELECT c FROM Concentration c where (c.member = :member) and (c.startDate like :date%)\n" +
            "       and (c.period = 'Day')")
    List<Concentration> findAllByMemberAndStartDateAndDay(@Param("member") Member member, @Param("date") String date);
    Optional<Concentration> findConcentrationByMemberAndStartDateAndPeriod(Member member, String startDate, String Period);
}
