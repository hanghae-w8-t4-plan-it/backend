package com.team4.planit.member;

import com.team4.planit.global.exception.CustomException;
import com.team4.planit.member.dto.LoginRequestDto;
import com.team4.planit.member.dto.MemberRequestDto;
import com.team4.planit.member.dto.MemberResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;

import static com.team4.planit.global.exception.ErrorCode.DUPLICATED_EMAIL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberServiceTest {
    @Autowired
    MemberService memberService;
    @Autowired
    MemberController memberController;

    @Test
    @Order(1)
    @DisplayName("회원 가입시 동일한 이메일 사용")
    void test1() {
        // given
        MemberRequestDto requestDto = new MemberRequestDto(
                "asdf@naver.com",
                "닉네임",
                "1");// 자동 완성 후 커서 위치
        HttpServletResponse response = new MockHttpServletResponse();


        // when
//        MemberResponseDto result = memberService.creatMember(requestDto, response);

        CustomException exception = assertThrows(CustomException.class, () -> {
            memberService.creatMember(requestDto, response);
        });
        // then
        assertEquals(DUPLICATED_EMAIL,exception.getErrorCode());
    }
    @Test
    @Order(2)
    @DisplayName("로그인")
    void test2() {
        // given
        LoginRequestDto requestDto = new LoginRequestDto(
                "asdf@naver.com",
                "1");// 자동 완성 후 커서 위치
        HttpServletResponse response = new MockHttpServletResponse();


        // when
        MemberResponseDto result = memberService.login(requestDto, response);


        // then
//        assertEquals(result.getNickname(), requestDto.getNickname());
//        System.out.println("-----닉네임체크 성공-----");
//        assertNotNull(result.getMemberId());
//        System.out.println("아이디 생성 성공");
    }

}