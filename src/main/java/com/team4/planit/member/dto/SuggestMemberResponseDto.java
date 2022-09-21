package com.team4.planit.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SuggestMemberResponseDto {
    private List<MemberResponseDto> recommendedMembers;
    private List<MemberResponseDto> achievementRank;
    private List<MemberResponseDto> concentrationRank;

    public SuggestMemberResponseDto(List<MemberResponseDto> recommendedMembers, List<MemberResponseDto> achievementRank, List<MemberResponseDto> concentrationRank) {
        this.recommendedMembers = recommendedMembers;
        this.achievementRank = achievementRank;
        this.concentrationRank = concentrationRank;
    }
}
