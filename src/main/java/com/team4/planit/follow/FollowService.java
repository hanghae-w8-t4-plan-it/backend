package com.team4.planit.follow;

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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final Check check;
    private final FollowRepository followRepository;

    public ResponseEntity<?> upDownFollow(Long memberId, HttpServletRequest request) {
        Member followingMember = check.validateMember(request);
        Member followedMember = check.isPresentMemberFollow(memberId);
        Optional<Follow> findFollowing = followRepository.findByMemberAndFollowedMember(followingMember, followedMember);
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

    public ResponseEntity<?> getFollowers(Long memberId, HttpServletRequest request) {
        check.validateMember(request);
        List<Follow> followList = followRepository.findAllByFollowedMemberId(memberId);
        List<FollowedResponseDto> followedResponseDtoList = new ArrayList<>();
        for(Follow follow : followList) {
            followedResponseDtoList.add(new FollowedResponseDto(follow.getMember().getId(), follow.getMember().getNickname(), follow.getMember().getProfilePhoto()));
        }
        return new ResponseEntity<>(Message.success(followedResponseDtoList), HttpStatus.OK);
    }

    public ResponseEntity<?> getFollowings(Long memberId, HttpServletRequest request) {
        check.validateMember(request);
        List<Follow> followList = followRepository.findAllByMemberId(memberId);
        List<FollowingResponseDto> followingResponseDtoList = new ArrayList<>();
        for(Follow follow : followList) {
            followingResponseDtoList.add(new FollowingResponseDto(
                    follow.getFollowedMember().getId(), follow.getFollowedMember().getNickname(), follow.getFollowedMember().getProfilePhoto()));
        }
        return new ResponseEntity<>(Message.success(followingResponseDtoList), HttpStatus.OK);
    }
}
