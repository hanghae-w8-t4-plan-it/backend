package com.team4.planit.member.socialLogin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class KakaoLoginController {

    private final KakaoLoginService kakaoLoginService;

    @GetMapping("/members/login/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        kakaoResponseDto responseDto = kakaoLoginService.kakaoLogin(code);
        responseDto.getTokenDto().tokenToHeaders(response);
        return new ResponseEntity<>(Message.success(
                new MemberResponseDto(
                        responseDto.getMember().getMemberId(),
                        responseDto.getMember().getNickname(),
                        responseDto.getMember().getProfileImgUrl(),
                        true)),
                HttpStatus.OK);
    }


}