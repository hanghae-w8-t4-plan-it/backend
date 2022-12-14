package com.team4.planit.member;


import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final Check check;

    @PostMapping("/register")
    public ResponseEntity<?> signup(@Valid @RequestBody MemberRequestDto requestDto, HttpServletResponse response) {
        MemberResponseDto memberResponseDto = memberService.creatMember(requestDto, response);
        return new ResponseEntity<>(Message.success(memberResponseDto), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        MemberResponseDto loginResponseDto = memberService.login(requestDto, response);
        return new ResponseEntity<>(Message.success(loginResponseDto), HttpStatus.OK);
    }

    @PostMapping("/register/check-email")
    public ResponseEntity<?> checkEmail(@Valid @RequestBody EmailRequestDto email) {
        check.checkEmail(email.getEmail());
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshTokenCheck(@RequestBody RefreshRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        memberService.refreshToken(requestDto, request, response);
        return new ResponseEntity<>(Message.success("ACCESS_TOKEN_REISSUE"), HttpStatus.OK);
    }

    @GetMapping("/suggest")
    public ResponseEntity<?> suggestMembers(HttpServletRequest request) {
        SuggestMemberResponseDto suggestMemberResponseDtoList = memberService.suggestMembers(request);
        return new ResponseEntity<>(Message.success(suggestMemberResponseDtoList), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteMembers(HttpServletRequest request){
        memberService.deleteMembers(request);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
    @PatchMapping
    public ResponseEntity<?> modifyMemberInfo(HttpServletRequest request,
                                              @Valid @RequestPart MemberRequestDto data,
                                              @RequestPart(required = false) MultipartFile[] image) throws IOException {
        memberService.modifyMemberInfo(request,data,image);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @GetMapping("/{memberId}/status")
    public ResponseEntity<?> memberProfile(HttpServletRequest request, @PathVariable Long memberId) {
        MemberProfileResponseDto memberProfileResponseDto = memberService.memberProfile(request, memberId);
        return new ResponseEntity<>(Message.success(memberProfileResponseDto), HttpStatus.OK);
    }
}
