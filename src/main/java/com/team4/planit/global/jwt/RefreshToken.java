package com.team4.planit.global.jwt;

import com.team4.planit.member.Member;
import com.team4.planit.global.shared.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken extends Timestamped {

    @Id
    @Column(name = "refresh_token_id", nullable = false)
    private Long refreshTokenId;

    @JoinColumn(name = "members_id",nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "refresh_token_value")
    private String value;

    public void updateValue(String token){
        this.value = token;
    }

}
