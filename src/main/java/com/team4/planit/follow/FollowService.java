package com.team4.planit.follow;

import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final Check check;
    private final FollowRepository followRepository;

    public ResponseEntity<?> upDownFollow(Long memberId, HttpServletRequest request) {
        Member followingMember = check.validateMember(request);
        check.checkAccessToken(request, followingMember);
        Member followedMember = check.isPresentMemberFollow(memberId);
        Optional<Follow> findFollowing = followRepository.findByFollowingMemberAndFollowedMember(followingMember, followedMember);
        if(findFollowing.isEmpty()) {
            FollowRequestDto followRequestDto = new FollowRequestDto(followingMember, followedMember);
            Follow follow = new Follow(followRequestDto);
            followRepository.save(follow);
            return new ResponseEntity<>(Message.success(true), HttpStatus.OK);
        } else {
            followRepository.deleteById(findFollowing.get().getId());
            return new ResponseEntity<>(Message.success(false), HttpStatus.OK);
        }
    }
}
