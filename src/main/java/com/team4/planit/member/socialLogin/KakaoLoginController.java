package com.team4.planit.member.socialLogin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.team4.planit.global.jwt.TokenDto;
import com.team4.planit.global.shared.Message;
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
        // authorizedCode: 카카오 서버로부터 받은 인가 코드
        TokenDto tokenDto = kakaoLoginService.kakaoLogin(code);
        tokenDto.tokenToHeaders(response);
        return new ResponseEntity<>(Message.success("로그인에 성공하였습니다."), HttpStatus.OK);
    }


}