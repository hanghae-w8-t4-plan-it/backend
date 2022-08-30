package com.team4.planit.global.shared;

import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.jwt.RefreshToken;
import com.team4.planit.global.jwt.TokenDto;
import com.team4.planit.global.jwt.TokenProvider;
import com.team4.planit.member.Member;
import com.team4.planit.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Check {
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public void checkMember(Member member) {
        if (member == null) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }

    public void checkEmail(String email) {
        if (null != isPresentMember(email)) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String password,Member member) {
        if (!member.validatePassword(passwordEncoder, password)) {
            throw new CustomException(ErrorCode.INVALID_MEMBER_INFO);
        }
    }

    public void checkAccessTokenExpiration(long accessTokenExpiration, Member requestingMember) {
        long now = (new Date().getTime());
        if (now < accessTokenExpiration) {
            tokenProvider.deleteRefreshToken(requestingMember);
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public void accessTokenCheck(HttpServletRequest request, Member member) {
        if (null == request.getHeader("Authorization")) throw new CustomException(ErrorCode.TOKEN_IS_EXPIRED);
        if (null == member) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }


    public ResponseEntity<Message> reissueAccessToken(HttpServletRequest request, HttpServletResponse response, Member requestingMember, RefreshToken refreshTokenConfirm) {
        if (refreshTokenConfirm == null) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED);
        }
        if (!Objects.equals(refreshTokenConfirm.getValue(), request.getHeader("Refresh-Token"))) {
            tokenProvider.deleteRefreshToken(requestingMember);
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        TokenDto tokenDto = tokenProvider.generateAccessToken(requestingMember);
        tokenToHeaders(tokenDto, response);
        return new ResponseEntity<>(Message.success("ACCESS_TOKEN_REISSUE"), HttpStatus.OK);
    }

    public Member isPresentEmail(String email) {
        Optional<Member> optionalLoginId = memberRepository.findByEmail(email);
        return optionalLoginId.orElse(null);
    }

    public Member isPresentMember(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    public Member validateMemberByRefreshToken(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }

    public void checkRequestingMember(Member requestingMember) {
        if(requestingMember==null)throw new CustomException(ErrorCode.INVALID_MEMBER_INFO);
    }
}











