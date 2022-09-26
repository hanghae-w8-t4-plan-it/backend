package com.team4.planit.statistic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcentrationRateResponseDto {
    private Float concentrationRate;
    private String startDate;

    public ConcentrationRateResponseDto(String startDate) {
        this.concentrationRate = 0f;
        this.startDate = startDate;
    }

    @Builder
    public ConcentrationRateResponseDto(Float concentrationRate, String startDate) {
        this.concentrationRate = concentrationRate;
        this.startDate = startDate;
    }
}
