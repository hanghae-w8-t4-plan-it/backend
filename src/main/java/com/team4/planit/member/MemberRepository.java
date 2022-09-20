package com.team4.planit.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(Long memberId);
    Optional<Member> findByEmail(String email);

    @Query(value = "SELECT * FROM member order by RAND() LIMIT 3", nativeQuery = true)
    List<Member> findRecommendedMember();

    @Query(value = "SELECT * FROM member m inner join achievement a on m.member_id = a.member_id\n" +
            "       where a.achievement_start_date = :date\n" +
            "       order by a.achievement_cnt desc LIMIT 3", nativeQuery = true)
    List<Member> findAchievementMember(@Param("date") LocalDate date);

    @Query(value = "SELECT * FROM member m inner join concentration c on c.member_id = m.member_id\n" +
            "       where c.concentration_start_date like :date%\n" +
            "       group by c.member_id" +
            "       order by sum(c.concentration_time) desc limit 3", nativeQuery = true)
    List<Member> findConcentrationMember(@Param("date") LocalDate date);
}