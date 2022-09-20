package com.team4.planit.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MemberResponseDto {
    private List<RecommendedMemberResponseDto> recommendedMembers;
    private List<AchievementRankResponseDto> achievementRank;
    private List<ConcentrationRankResponseDto> concentrationRank;

    public MemberResponseDto(List<RecommendedMemberResponseDto> recommendedMembers, List<AchievementRankResponseDto> achievementRank, List<ConcentrationRankResponseDto> concentrationRank) {
        this.recommendedMembers = recommendedMembers;
        this.achievementRank = achievementRank;
        this.concentrationRank = concentrationRank;
    }
}
