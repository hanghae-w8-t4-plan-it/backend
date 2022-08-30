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
    @Column(nullable = false)
    private Long id;

    @JoinColumn(name = "members_id",nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "token_value", nullable = false)
    private String value;

    public void updateValue(String token){
        this.value = token;
    }

}
