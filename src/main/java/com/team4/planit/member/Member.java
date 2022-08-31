package com.team4.planit.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team4.planit.global.shared.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.Objects;

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
    private String profilePhoto;

    @Column(unique = true)
    private Long kakaoId;

    @Builder
    public Member(String email, String nickname, String password) {
        this.id = getId();
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.profilePhoto = "https://springbucketss.s3.ap-northeast-2.amazonaws.com/basicprofile.png";
    }

    @Builder
    public Member(String email, String password, String profilePhoto, String nickname, Long kakaoId) {
        this.id = getId();
        this.email = email;
        this.password = password;
        this.profilePhoto = profilePhoto;
        this.nickname = nickname;
        this.kakaoId = kakaoId;
    }


    public boolean validatePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return id != null && Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
