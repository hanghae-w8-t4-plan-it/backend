package com.team4.planit.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MostConcentrationTimeResponseDto {
    private Integer startTime;
    private Integer endTime;
    private Integer totalTime;

    public MostConcentrationTimeResponseDto(String startTime, Integer totalTime) {
        this.startTime = Integer.parseInt(startTime);
        this.endTime = this.startTime + 1;
        this.totalTime = totalTime;
    }
}
