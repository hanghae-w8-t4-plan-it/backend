package com.team4.planit.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefreshRequestDto {
    private Long memberId;

    public RefreshRequestDto(Long memberId) {
        this.memberId = memberId;
    }
}
