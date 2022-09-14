package com.team4.planit.statistic.achievement;

import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
    Optional<Achievement> findAllByStartDateAndMember(String startDate, Member member);
}
