package com.team4.planit.statistic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AchievementResponseDto {
    private Float achievementRate;
    private Integer achievementCnt;

    public AchievementResponseDto(Float achievementRate, Integer achievementCnt) {
        this.achievementRate = achievementRate;
        this.achievementCnt = achievementCnt;
    }
}
