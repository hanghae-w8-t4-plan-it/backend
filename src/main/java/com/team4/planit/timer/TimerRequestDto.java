package com.team4.planit.timer;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TimerRequestDto {
    private String setTime;
    private String remainTime;

    public TimerRequestDto(String setTime, String remainTime) {
        this.setTime = setTime;
        this.remainTime = remainTime;
    }
}
