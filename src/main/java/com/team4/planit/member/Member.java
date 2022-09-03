package com.team4.planit.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team4.planit.global.shared.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String profileImgUrl;

    @Column(unique = true)
    private Long kakaoId;

    @Builder
    public Member(String email, String nickname, String password) {
        this.id = getId();
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profileImgUrl = "https://springbucketss.s3.ap-northeast-2.amazonaws.com/basicprofile.png";
    }

    @Builder
    public Member(String email, String password, String profileImgUrl, String nickname, Long kakaoId) {
        this.id = getId();
        this.email = email;
        this.password = password;
        this.profileImgUrl = profileImgUrl;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
    }
    public void update(MemberRequestDto requestDto, String imgUrl) {
        if(requestDto.getEmail()!=null) this.email = requestDto.getEmail();
        if(requestDto.getPassword()!=null) this.password = requestDto.getPassword();
        if(requestDto.getNickname()!=null) this.nickname = requestDto.getNickname();
        if(imgUrl!=null) this.profileImgUrl =imgUrl;
    }


    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }


}
