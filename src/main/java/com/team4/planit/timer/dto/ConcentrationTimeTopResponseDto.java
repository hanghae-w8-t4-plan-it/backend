package com.team4.planit.timer.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ConcentrationTimeTopResponseDto {
    private Integer sumElapsedTime;
    private List<String> data;

    public ConcentrationTimeTopResponseDto(Integer sumElapsedTime, List<String> data) {
        this.sumElapsedTime = sumElapsedTime;
        this.data = data;
    }
}
