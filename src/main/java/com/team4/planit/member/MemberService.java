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
import com.team4.planit.global.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public ResponseEntity<?> creatMember(MemberRequestDto requestDto) {
        check.checkEmail(requestDto.getEmail());
        Member member = new Member(requestDto.getEmail(), requestDto.getNickname(), passwordEncoder.encode(requestDto.getPassword()));
        memberRepository.save(member);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        Member member = check.isPresentMember(requestDto.getEmail());
        check.checkPassword(passwordEncoder, requestDto.getPassword(), member);
        LoginResponseDto loginResponseDto = new LoginResponseDto(member.getMemberId(), member.getNickname(), member.getProfileImgUrl());
        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        check.tokenToHeaders(tokenDto, response);
        return new ResponseEntity<>(Message.success(loginResponseDto), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> checkEmail(String email) {
        check.checkEmail(email);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    public ResponseEntity<?> refreshToken(RefreshRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        tokenProvider.validateToken(request.getHeader("RefreshToken"));
        Member member = memberRepository.findByMemberId(requestDto.getMemberId()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_MEMBER_INFO));
        long accessTokenExpiration = Long.parseLong(request.getHeader("AccessTokenExpireTime"));
        check.checkAccessTokenExpiration(accessTokenExpiration, member);
        RefreshToken refreshTokenConfirm = refreshTokenRepository.findByMember(member).orElse(null);
        return check.reissueAccessToken(request, response, member, refreshTokenConfirm);
    }

    public ResponseEntity<?> suggestMembers() {
        List<Member> memberList = memberRepository.findSuggestMember();
        List<MemberResponseDto> memberResponseDtoList = new ArrayList<>();
        for (Member member : memberList) {
            memberResponseDtoList.add(MemberResponseDto.builder()
                    .id(member.getMemberId())
                    .nickname(member.getNickname())
                    .profileImgUrl(member.getProfileImgUrl())
                    .build());
        }
        return new ResponseEntity<>(Message.success(memberResponseDtoList), HttpStatus.OK);
    }

    public ResponseEntity<?> deleteMembers(HttpServletRequest request) {
        Member member = check.validateMember(request);
        member.updateStatus();
        memberRepository.save(member);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> modifyMemberInfo(HttpServletRequest request, MemberRequestDto requestDto, MultipartFile[] image) throws IOException {
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
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    public ResponseEntity<?> memberProfile(HttpServletRequest request, Long memberId) {
        check.validateMember(request);
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(null);
        MemberProfileResponseDto memberProfileResponseDto = MemberProfileResponseDto.builder()
                .memberId(member.getMemberId())
                .nickname(member.getNickname())
                .profileImgUrl(member.getProfileImgUrl())
                .followerCnt(followRepository.countAllByFollowedMember(member))
                .followingCnt(followRepository.countAllByMember(member))
                .build();
        return new ResponseEntity<>(Message.success(memberProfileResponseDto), HttpStatus.OK);
    }
}
