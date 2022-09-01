package com.team4.planit.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{memberId}")
    public ResponseEntity<?> follow(@PathVariable Long memberId, HttpServletRequest request) {
        return followService.upDownFollow(memberId, request);
    }
}
