package com.team4.planit.statistic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AchievementRateResponseDto {
    private Float achievementRate;
    private String startDate;

    public AchievementRateResponseDto(String startDate) {
        this.achievementRate = 0f;
        this.startDate = startDate;
    }

    @Builder
    public AchievementRateResponseDto(Float achievementRate, String startDate) {
        this.achievementRate = achievementRate;
        this.startDate = startDate;
    }
}
