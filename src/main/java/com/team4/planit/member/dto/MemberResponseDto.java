package com.team4.planit.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private List<RecommendedMemberResponseDto> recommendedMember;
    private List<AchievementMemberResponseDto> achievementMember;
    private List<ConcentrationMemberResponseDto> concentrationMember;

    public MemberResponseDto(List<RecommendedMemberResponseDto> recommendedMember, List<AchievementMemberResponseDto> achievementMember, List<ConcentrationMemberResponseDto> concentrationMember) {
        this.recommendedMember = recommendedMember;
        this.achievementMember = achievementMember;
        this.concentrationMember = concentrationMember;
    }
}
