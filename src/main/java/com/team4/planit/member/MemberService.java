package com.team4.planit.member;


import com.team4.planit.file.FileService;
import com.team4.planit.follow.FollowRepository;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.jwt.RefreshToken;
import com.team4.planit.global.jwt.RefreshTokenRepository;
import com.team4.planit.global.jwt.TokenDto;
import com.team4.planit.global.jwt.TokenProvider;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Check check;
    private final FileService fileService;
    private final FollowRepository followRepository;

    @Transactional
    public void creatMember(MemberRequestDto requestDto, HttpServletResponse response) {
        check.checkEmail(requestDto.getEmail());
        Member member = new Member(requestDto.getEmail(), requestDto.getNickname(), passwordEncoder.encode(requestDto.getPassword()));
        memberRepository.save(member);
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        check.tokenToHeaders(tokenDto, response);
    }

    @Transactional
    public MemberResponseDto login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = check.isPresentMember(requestDto.getEmail());
        check.checkPassword(passwordEncoder, requestDto.getPassword(), member);
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        check.tokenToHeaders(tokenDto, response);
        return new MemberResponseDto(member.getMemberId(), member.getNickname(), member.getProfileImgUrl());
    }

    public void refreshToken(RefreshRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        tokenProvider.validateToken(request.getHeader("RefreshToken"));
        Member member = memberRepository.findByMemberId(requestDto.getMemberId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_MEMBER_INFO));
        long accessTokenExpiration = Long.parseLong(request.getHeader("AccessTokenExpireTime"));
        check.checkAccessTokenExpiration(accessTokenExpiration, member);
        RefreshToken refreshTokenConfirm = refreshTokenRepository.findByMember(member)
                .orElseThrow(()->new CustomException(ErrorCode.REFRESH_TOKEN_IS_EXPIRED));
        check.reissueAccessToken(request, response, member, refreshTokenConfirm);
    }

    @Transactional(readOnly = true)
    public SuggestMemberResponseDto suggestMembers(HttpServletRequest request) {
        check.validateMember(request);
        LocalDate date = LocalDate.now();
        List<Member> recommendedMemberList = memberRepository.findRecommendedMember();
        List<Member> achievementMemberList = memberRepository.findAchievementMember(date);
        List<Member> concentrationMemberList = memberRepository.findConcentrationMember(date);
        List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();
        for (Member member : recommendedMemberList) {
            memberResponseDtoList.add(
                    MemberResponseDto.builder()
                            .memberId(member.getMemberId())
                            .nickname(member.getNickname())
                            .profileImgUrl(member.getProfileImgUrl())
                            .build());
        }
        List<MemberResponseDto> achievementRankResponseDtoList = new ArrayList<>();
        for (Member member : achievementMemberList) {
            achievementRankResponseDtoList.add(
                    MemberResponseDto.builder()
                            .memberId(member.getMemberId())
                            .nickname(member.getNickname())
                            .profileImgUrl(member.getProfileImgUrl())
                            .build());
        }
        List<MemberResponseDto> concentrationRankResponseDtoList = new ArrayList<>();
        for (Member member : concentrationMemberList) {
            concentrationRankResponseDtoList.add(
                    MemberResponseDto.builder()
                            .memberId(member.getMemberId())
                            .nickname(member.getNickname())
                            .profileImgUrl(member.getProfileImgUrl())
                            .build());
        }
        return new SuggestMemberResponseDto(memberResponseDtoList, achievementRankResponseDtoList, concentrationRankResponseDtoList);
    }

    public void deleteMembers(HttpServletRequest request) {
        Member member = check.validateMember(request);
        member.updateStatus();
        memberRepository.save(member);
    }

    @Transactional
    public void modifyMemberInfo(HttpServletRequest request, MemberRequestDto requestDto, MultipartFile[] image) throws IOException {
        Member member = check.validateMember(request);
        String imgUrl = null;
        if(image!=null) imgUrl = fileService.getImgUrl(image);
        MemberRequestDto encodedRequestDto= MemberRequestDto.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();
        member.update(encodedRequestDto,imgUrl);
        memberRepository.save(member);
    }

    public  MemberProfileResponseDto memberProfile(HttpServletRequest request, Long memberId) {
        check.validateMember(request);
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(null);
        return MemberProfileResponseDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .profileImgUrl(member.getProfileImgUrl())
                .followerCnt(followRepository.countAllByFollowedMember(member))
                .followingCnt(followRepository.countAllByMember(member))
                .build();
    }
}
