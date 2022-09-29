package com.team4.planit.statistic.achievement;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AchievementResponseDto {
    Byte planetLevel;
    Integer achievementCnt;

    public AchievementResponseDto(Byte planetLevel, Integer achievementCnt) {
        this.planetLevel = planetLevel;
        this.achievementCnt = achievementCnt;
    }
}
