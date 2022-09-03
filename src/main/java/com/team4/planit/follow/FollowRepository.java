package com.team4.planit.follow;

import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByMemberAndFollowedMember(Member followingmember, Member followedmember);

    List<Follow> findAllByFollowedMemberId(Long memberId);

    List<Follow> findAllByMemberId(Long memberId);
}
