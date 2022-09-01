package com.team4.planit.follow;

import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowingMemberAndFollowedMember(Member followingmember, Member followedmember);
}
