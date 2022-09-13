package com.team4.planit.statistic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ConcentrationResponseDto {
    private Float concentrationRate;
    private Integer concentrationTime;

    public ConcentrationResponseDto(Float concentrationRate, Integer concentrationTime) {
        this.concentrationRate = concentrationRate;
        this.concentrationTime = concentrationTime;
    }
}
