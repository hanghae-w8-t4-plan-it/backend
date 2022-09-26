package com.team4.planit.statistic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatisticPeriodRateResponseDto {
    private String startDate;
    private Float rate;

    public StatisticPeriodRateResponseDto(String startDate) {
        this.startDate = startDate;
        this.rate = 0f;
    }
}
