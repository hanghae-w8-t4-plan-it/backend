package com.team4.planit.member;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/register")
    public ResponseEntity<?> signup(@RequestBody MemberRequestDto requestDto) {
        return memberService.creatMember(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.login(requestDto, response);
    }

    @PostMapping("/register/check-email")
    public ResponseEntity<?> checkEmail(@RequestBody MemberRequestDto requestDto) {
        return memberService.checkEmail(requestDto.getEmail());
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTokenCheck(@RequestBody RefreshRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        return memberService.refreshToken(requestDto, request, response);
    }

    @GetMapping("/suggest")
    public ResponseEntity<?> suggestMembers() {
        return memberService.suggestMembers();
    }
    @DeleteMapping
    public ResponseEntity<?> deleteMembers(HttpServletRequest request){
        return memberService.deleteMembers(request);
    }
    @PatchMapping
    public ResponseEntity<?> modifyMemberInfo(HttpServletRequest request,
                                              @RequestPart MemberRequestDto requestDto,
                                              @RequestPart(required = false) MultipartFile[] image) throws IOException {
        return memberService.modifyMemberInfo(request,requestDto,image);
    }
}
