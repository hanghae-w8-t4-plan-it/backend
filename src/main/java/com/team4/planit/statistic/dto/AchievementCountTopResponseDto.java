package com.team4.planit.statistic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class AchievementCountTopResponseDto {
    private Integer maxAchievementCount;
    private List<String> data;

    public AchievementCountTopResponseDto(Integer maxAchievementCount, List<String> data) {
        this.maxAchievementCount = maxAchievementCount;
        this.data = data;
    }
}
