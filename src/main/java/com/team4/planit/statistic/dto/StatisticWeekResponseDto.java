package com.team4.planit.statistic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StatisticWeekResponseDto {
    private Long memberId;
    private List<AchievementResponseDto> achievementRates;
}
