package com.team4.planit.follow;

import com.team4.planit.global.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow/{memberId}")
public class FollowController {
    private final FollowService followService;

    @PostMapping
    public ResponseEntity<?> follow(@PathVariable Long memberId, HttpServletRequest request) {
        Boolean isSuccess = followService.upDownFollow(memberId, request);
        return new ResponseEntity<>(Message.success(isSuccess), HttpStatus.OK);
    }

    @GetMapping("/followers")
    public ResponseEntity<?> getFollowers(@PathVariable Long memberId, HttpServletRequest request) {
        return followService.getFollowers(memberId, request);
    }

    @GetMapping("/followings")
    public ResponseEntity<?> getFollowings(@PathVariable Long memberId, HttpServletRequest request) {
        return followService.getFollowings(memberId, request);
    }
}
