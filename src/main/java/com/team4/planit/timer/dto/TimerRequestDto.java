package com.team4.planit.timer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TimerRequestDto {
    private Integer setTime;
    private Integer elapsedTime;

    @Builder
    public TimerRequestDto(Integer setTime, Integer elapsedTime) {
        this.setTime = setTime;
        this.elapsedTime = elapsedTime;
    }
}
