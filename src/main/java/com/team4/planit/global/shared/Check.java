package com.team4.planit.global.shared;

import com.team4.planit.category.Category;
import com.team4.planit.category.CategoryRepository;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.jwt.RefreshToken;
import com.team4.planit.global.jwt.TokenDto;
import com.team4.planit.global.jwt.TokenProvider;
import com.team4.planit.member.Member;
import com.team4.planit.member.MemberRepository;
import com.team4.planit.todoList.TodoList;
import com.team4.planit.todoList.TodoListRepository;
import com.team4.planit.todoList.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class Check {
    private final CategoryRepository categoryRepository;
    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;

    public Integer countByCategory(Category category) {
        return todoRepository.countAllByCategory(category);
    }


    public void checkCategoryAuthor(Member member, Category category) {
        if (!category.getMember().getEmail().equals(member.getEmail())) throw new CustomException(ErrorCode.NOT_AUTHOR);
    }

    public void checkEmail(String email) {
        if (null != memberRepository.findByEmail(email).orElse(null)) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
    }

    public void checkPassword(PasswordEncoder passwordEncoder, String password, Member member) {
        if (!member.validatePassword(passwordEncoder, password) || member.getMemberStatus().equals("deleted")) {
            throw new CustomException(ErrorCode.INVALID_MEMBER_INFO);
        }
    }

    public void checkAccessTokenExpiration(long accessTokenExpiration, Member member) {
        long now = (new Date().getTime());
        if (now < accessTokenExpiration) {
            tokenProvider.deleteRefreshToken(member);
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    public ResponseEntity<Message> reissueAccessToken(HttpServletRequest request, HttpServletResponse response, Member member, RefreshToken refreshTokenConfirm) {
        if (refreshTokenConfirm == null) {
            throw new CustomException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED);
        }
        if (!Objects.equals(refreshTokenConfirm.getValue(), request.getHeader("RefreshToken"))) {
            tokenProvider.deleteRefreshToken(member);
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        TokenDto tokenDto = tokenProvider.generateAccessToken(member);
        tokenToHeaders(tokenDto, response);
        return new ResponseEntity<>(Message.success("ACCESS_TOKEN_REISSUE"), HttpStatus.OK);
    }

    public Member isPresentMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Category isPresentCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public TodoList isPresentTodoList(Long id) {
        return todoListRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.TODO_LIST_NOT_FOUND));
    }

    public Member isPresentMemberByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public Member validateMember(HttpServletRequest request) {
        if (request.getHeader("Authorization").length() < 8) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        if (!tokenProvider.validateToken(request.getHeader("Authorization").substring(7))) {
            throw new CustomException(ErrorCode.TOKEN_IS_EXPIRED);
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("RefreshToken", tokenDto.getRefreshToken());
        response.addHeader("AccessTokenExpireTime", tokenDto.getAccessTokenExpiresIn().toString());
    }

}











