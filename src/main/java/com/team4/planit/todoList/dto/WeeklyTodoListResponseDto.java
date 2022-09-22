package com.team4.planit.todoList.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class WeeklyTodoListResponseDto {
    private String nickname;
    private Integer weeklyTotalAchievement;
    private Integer weeklyTotalLikes;
//    private List<TodoList> planets;
    private List<WeeklyPlanetResponseDto> planets;

    @Builder
    public WeeklyTodoListResponseDto(String nickname, Integer weeklyTotalAchievement,
                                     Integer weeklyTotalLikes, List<WeeklyPlanetResponseDto> planets) {
            this.nickname = nickname;
            this.weeklyTotalAchievement = weeklyTotalAchievement;
            this.weeklyTotalLikes = weeklyTotalLikes;
            this.planets = planets;
    }
}
