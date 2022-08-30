package com.team4.planit.global.shared;

import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.jwt.TokenProvider;
import com.team4.planit.member.Member;
import com.team4.planit.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Check {
    private  final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    public void memberCheck(Member member){
        if(member==null) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }

    public void accessTokenCheck(HttpServletRequest request, Member member) {
        if (null == request.getHeader("Authorization")) throw new CustomException(ErrorCode.TOKEN_IS_EXPIRED);
        if (null == member) throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
    }
    @Transactional(readOnly = true)
    public Member isPresentLoginId(String Email) {
        Optional<Member> optionalLoginId = memberRepository.findByEmail(Email);
        return optionalLoginId.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
}











