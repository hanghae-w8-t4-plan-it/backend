package com.team4.planit.statistic.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcentrationResponseDto {
    private Float concentrationRate;
    private String startDate;

    public ConcentrationResponseDto(String startDate) {
        this.concentrationRate = 0f;
        this.startDate = startDate;
    }

    @Builder
    public ConcentrationResponseDto(Float concentrationRate, String startDate) {
        this.concentrationRate = concentrationRate;
        this.startDate = startDate;
    }
}
