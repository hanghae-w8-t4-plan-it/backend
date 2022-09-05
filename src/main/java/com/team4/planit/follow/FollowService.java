package com.team4.planit.follow;

import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final Check check;
    private final FollowRepository followRepository;

    public ResponseEntity<?> upDownFollow(Long memberId, HttpServletRequest request) {
        Member followingMember = check.validateMember(request);
        Member followedMember = check.isPresentMemberByMemberId(memberId);
        if(followingMember.getMemberId().equals(memberId)) { return new ResponseEntity<>(Message.success(ErrorCode.FOLLOW_SELF_ERROR), HttpStatus.OK); }
        Follow findFollowing = followRepository.findByMemberAndFollowedMember(followingMember, followedMember).orElse(null);
        if(findFollowing==null) {
            followRepository.save(new Follow(followingMember, followedMember));
            return new ResponseEntity<>(Message.success(true), HttpStatus.OK);
        }
            followRepository.deleteById(findFollowing.getFollowId());
            return new ResponseEntity<>(Message.success(false), HttpStatus.OK);
    }

    public ResponseEntity<?> getFollowers(Long memberId, HttpServletRequest request) {
        check.validateMember(request);
        Member member = check.isPresentMemberByMemberId(memberId);
        List<Follow> followList = followRepository.findAllByFollowedMember(member);
        List<FollowedResponseDto> followedResponseDtoList = new ArrayList<>();
        for(Follow follow : followList) {
            followedResponseDtoList.add(new FollowedResponseDto(follow.getMember().getMemberId(), follow.getMember().getNickname(), follow.getMember().getProfileImgUrl()));
        }
        return new ResponseEntity<>(Message.success(followedResponseDtoList), HttpStatus.OK);
    }

    public ResponseEntity<?> getFollowings(Long memberId, HttpServletRequest request) {
        check.validateMember(request);
        Member member = check.isPresentMemberByMemberId(memberId);
        List<Follow> followList = followRepository.findAllByMember(member);
        List<FollowingResponseDto> followingResponseDtoList = new ArrayList<>();
        for(Follow follow : followList) {
            followingResponseDtoList.add(new FollowingResponseDto(follow.getFollowedMember().getMemberId(), follow.getFollowedMember().getNickname(), follow.getFollowedMember().getProfileImgUrl()));
        }
        return new ResponseEntity<>(Message.success(followingResponseDtoList), HttpStatus.OK);
    }
}
