package com.team4.planit.statistic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class StatisticDayResponseDto {
    private Long memberId;
    private Float achievementRate;
    private Integer achievementCnt;
    private List<ConcentrationRateResponseDto> concentrationRates;

    @Builder
    public StatisticDayResponseDto(Long memberId, Float achievementRate, Integer achievementCnt, List<ConcentrationRateResponseDto> concentrationRates) {
        this.memberId = memberId;
        this.achievementRate = achievementRate;
        this.achievementCnt = achievementCnt;
        this.concentrationRates = concentrationRates;
    }
}
