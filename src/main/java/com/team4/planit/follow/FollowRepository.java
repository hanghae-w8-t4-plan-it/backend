package com.team4.planit.follow;

import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByMemberAndFollowedMember(Member followingmember, Member followedmember);

    @Query(value = "select DISTINCT m from Follow m left join fetch m.member where (m.followedMember in :member)")
    List<Follow> findAllByFollowedMember(@Param("member") Member member);

    @Query(value = "select DISTINCT m from Follow m left join fetch m.followedMember where (m.member in :member)")
    List<Follow> findAllByMember(@Param("member") Member member);
}
