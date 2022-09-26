package com.team4.planit.statistic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
@Getter
@NoArgsConstructor
public class StatisticPeriodResponseDto {
    private List<AchievementRateResponseDto> achievementRates;
    private List<ConcentrationRateResponseDto> concentrationRates;

    public StatisticPeriodResponseDto(List<AchievementRateResponseDto> achievementRates, List<ConcentrationRateResponseDto> concentrationRates) {
        this.achievementRates = achievementRates;
        this.concentrationRates = concentrationRates;
    }
}
