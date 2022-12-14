package com.team4.planit.member.socialLogin;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team4.planit.global.jwt.TokenProvider;
import com.team4.planit.global.jwt.UserDetailsImpl;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class KakaoLoginService {
    private final TokenProvider tokenProvider;

    private final MemberRepository memberRepository;
    private final Check check;
    private final PasswordEncoder passwordEncoder;

    @Value("${myKaKaoRestAplKey}")
    private String myKaKaoRestAplKey;

    public kakaoResponseDto kakaoLogin(String code) throws JsonProcessingException {
        String accessToken = getAccessToken(code);
        KakaoMemberInfoDto kakaoMemberInfo = getKakaoMemberInfo(accessToken);
        String email = kakaoMemberInfo.getEmail();
        Member kakaoMember = memberRepository.findByEmail(email)
                .orElse(null);
        if (kakaoMember == null) {
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            String profileImgUrl = kakaoMemberInfo.getProfileImgUrl();
            String nickname = kakaoMemberInfo.getNickname();
            Long kakaoId = kakaoMemberInfo.getId();
            kakaoMember = new Member(email, encodedPassword, profileImgUrl, nickname, kakaoId);
            memberRepository.save(kakaoMember);
        }

        UserDetails userDetails = new UserDetailsImpl(kakaoMember);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Member member = check.isPresentMember(kakaoMember.getEmail());
        return new kakaoResponseDto(tokenProvider.generateTokenDto(member),member);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", myKaKaoRestAplKey);
        body.add("redirect_uri", "https://planit-todo.com/login/kakao");
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoMemberInfoDto getKakaoMemberInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoMemberInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoMemberInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("kakao_account").get("profile")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText() + "#kakao";
        String profileImgUrl = jsonNode.get("kakao_account").get("profile").get("profile_image_url").asText();
        return new KakaoMemberInfoDto(nickname, email, profileImgUrl, id);
    }
}

