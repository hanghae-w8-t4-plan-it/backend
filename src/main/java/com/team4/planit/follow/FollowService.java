package com.team4.planit.follow;

import com.team4.planit.follow.dto.FollowedResponseDto;
import com.team4.planit.follow.dto.FollowingResponseDto;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final Check check;
    private final FollowRepository followRepository;

    @Transactional
    public Boolean upDownFollow(Long memberId, HttpServletRequest request) {
        Member followingMember = check.validateMember(request);
        Member followedMember = check.isPresentMemberByMemberId(memberId);
        if(followingMember.getMemberId().equals(memberId)) { throw new CustomException(ErrorCode.FOLLOW_SELF_ERROR); }
        Follow findFollowing = followRepository.findByMemberAndFollowedMember(followingMember, followedMember).orElse(null);
        if(findFollowing==null) {
            followRepository.save(new Follow(followingMember, followedMember));
            return true;
        }
            followRepository.deleteById(findFollowing.getFollowId());
            return false;
    }

    @Transactional(readOnly = true)
    public List<FollowedResponseDto> getFollowers(Long memberId, HttpServletRequest request) {
        check.validateMember(request);
        Member member = check.isPresentMemberByMemberId(memberId);
        List<Follow> followList = followRepository.findAllByFollowedMember(member);
        List<FollowedResponseDto> followedResponseDtoList = new ArrayList<>();
        for(Follow follow : followList) {
            followedResponseDtoList.add(new FollowedResponseDto(follow.getMember().getMemberId(), follow.getMember().getNickname(), follow.getMember().getProfileImgUrl()));
        }
        return followedResponseDtoList;
    }

    @Transactional(readOnly = true)
    public List<FollowingResponseDto> getFollowings(Long memberId, HttpServletRequest request) {
        check.validateMember(request);
        Member member = check.isPresentMemberByMemberId(memberId);
        List<Follow> followList = followRepository.findAllByMember(member);
        List<FollowingResponseDto> followingResponseDtoList = new ArrayList<>();
        for(Follow follow : followList) {
            followingResponseDtoList.add(new FollowingResponseDto(follow.getFollowedMember().getMemberId(), follow.getFollowedMember().getNickname(), follow.getFollowedMember().getProfileImgUrl()));
        }
        return followingResponseDtoList;
    }
}
