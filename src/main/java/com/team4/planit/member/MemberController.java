package com.team4.planit.member;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody MemberRequestDto requestDto){
        return memberService.creatMember(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?>login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response){
        return memberService.login(requestDto, response);
    }

    @PostMapping("/register/check-email")
    public ResponseEntity<?> emailCheck(@RequestBody String email){
        return memberService.emailCheck(email);
    }


    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTokenCheck( HttpServletRequest request, HttpServletResponse response){
        return memberService.refreshToken(request, response);
    }
}
