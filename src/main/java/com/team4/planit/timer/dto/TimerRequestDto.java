package com.team4.planit.timer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TimerRequestDto {
    private Integer setTime;
    private Integer remainTime;

    @Builder
    public TimerRequestDto(Integer setTime, Integer remainTime) {
        this.setTime = setTime;
        this.remainTime = remainTime;
    }
}
