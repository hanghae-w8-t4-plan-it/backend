package com.team4.planit.follow;

import com.team4.planit.follow.dto.FollowedResponseDto;
import com.team4.planit.follow.dto.FollowingResponseDto;
import com.team4.planit.global.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow/{memberId}")
public class FollowController {
    private final FollowService followService;

    @PostMapping
    public ResponseEntity<?> follow(@PathVariable Long memberId, HttpServletRequest request) {
        Boolean isFollowSuccess = followService.upDownFollow(memberId, request);
        return new ResponseEntity<>(Message.success(isFollowSuccess), HttpStatus.OK);
    }

    @GetMapping("/followers")
    public ResponseEntity<?> getFollowers(@PathVariable Long memberId, HttpServletRequest request) {
        List<FollowedResponseDto> followedResponseDtoList = followService.getFollowers(memberId, request);
        return new ResponseEntity<>(Message.success(followedResponseDtoList), HttpStatus.OK);
    }

    @GetMapping("/followings")
    public ResponseEntity<?> getFollowings(@PathVariable Long memberId, HttpServletRequest request) {
        List<FollowingResponseDto> followingResponseDtoList = followService.getFollowings(memberId, request);
        return new ResponseEntity<>(Message.success(followingResponseDtoList), HttpStatus.OK);
    }
}
