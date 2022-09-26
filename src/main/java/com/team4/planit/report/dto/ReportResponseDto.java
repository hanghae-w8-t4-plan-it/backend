package com.team4.planit.report.dto;

import com.team4.planit.statistic.dto.AchievementCountTopResponseDto;
import com.team4.planit.timer.dto.ConcentrationTimeTopResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReportResponseDto {

    private List<String> categoryRank;
    private AchievementCountTopResponseDto achievementCountTop;
    private ConcentrationTimeTopResponseDto concentrationTimeTop;
    private MostConcentrationTimeResponseDto mostConcentrationTime;
    private Integer achievementCombo;
    private Integer monthlyTotalLikes;
    private MostLikeResponseDto mostLikeDates;
    private MostLikeResponseDto mostLikeMembers;

    @Builder
    public ReportResponseDto(List<String> categoryRank, AchievementCountTopResponseDto achievementCountTop,
                             ConcentrationTimeTopResponseDto concentrationTimeTop, MostConcentrationTimeResponseDto mostConcentrationTime,
                             Integer achievementCombo, Integer monthlyTotalLikes,
                             MostLikeResponseDto mostLikeDates, MostLikeResponseDto mostLikeMembers) {
        this.categoryRank = categoryRank;
        this.achievementCountTop = achievementCountTop;
        this.concentrationTimeTop = concentrationTimeTop;
        this.mostConcentrationTime = mostConcentrationTime;
        this.achievementCombo = achievementCombo;
        this.monthlyTotalLikes = monthlyTotalLikes;
        this.mostLikeDates = mostLikeDates;
        this.mostLikeMembers = mostLikeMembers;
    }

}
