package com.team4.planit.member;


import com.team4.planit.global.jwt.RefreshToken;
import com.team4.planit.global.jwt.RefreshTokenRepository;
import com.team4.planit.global.jwt.TokenDto;
import com.team4.planit.global.jwt.TokenProvider;
import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Check check;

    @Transactional
    public ResponseEntity<?> creatMember(MemberRequestDto requestDto) {
        check.checkEmail(requestDto.getEmail());
        Member member = new Member(requestDto.getEmail(), requestDto.getNickname(), passwordEncoder.encode(requestDto.getPassword()));
        memberRepository.save(member);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = check.isPresentMember(requestDto.getEmail());
        check.checkMember(member);
        check.checkPassword(passwordEncoder, requestDto.getPassword(), member);
        String nickname = member.getNickname();
        String photoUrl = member.getProfilePhoto();
        LoginResponseDto loginResponseDto = new LoginResponseDto(nickname, photoUrl);
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        check.tokenToHeaders(tokenDto, response);
        return new ResponseEntity<>(Message.success(loginResponseDto), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> checkEmail(String email) {
        check.checkEmail(email);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    public ResponseEntity<?> refreshToken(MemberRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        tokenProvider.validateToken(request.getHeader("RefreshToken"));
        Member member = memberRepository.findByEmail(requestDto.getEmail()).orElse(null);
        check.checkRequestingMember(member);
        long accessTokenExpiration = Long.parseLong(request.getHeader("AccessTokenExpireTime"));
        check.checkAccessTokenExpiration(accessTokenExpiration, member);
        RefreshToken refreshTokenConfirm = refreshTokenRepository.findByMember(member).orElse(null);
        return check.reissueAccessToken(request, response, member, refreshTokenConfirm);
    }
}
