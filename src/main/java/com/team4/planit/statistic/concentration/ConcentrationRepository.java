package com.team4.planit.statistic.concentration;

import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConcentrationRepository extends JpaRepository<Concentration, Long> {
    @Query(value = "SELECT c FROM Concentration c where (c.member = :member) and (c.period = 'Day')" +
            "and (c.startDate like :date%)")
    List<Concentration> findAllByMemberAndStartDateAndDay(@Param("member") Member member, @Param("date") String date);
    @Query(value = "SELECT c FROM Concentration c where (c.member.memberId = :memberId) and (c.startDate between :startDate" +
            "       and :endDate)  and (c.period = :period)")
    List<Concentration> findAllByMemberAndPeriod(@Param("memberId")Long memberId,  @Param("period")String period ,
                                                 @Param("startDate")String startDate,  @Param("endDate")String endDate);
    Optional<Concentration> findConcentrationByMemberAndStartDateAndPeriod(Member member, String startDate, String Period);
}
