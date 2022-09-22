package com.team4.planit.todoList.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WeeklyPlanetResponseDto {
    private Long todoListId;
    private String dueDate;
    private Byte planetType;
    private Short planetSize;
    private Byte planetColor;
    private Byte planetLevel;

    public WeeklyPlanetResponseDto(String dueDate) {
        this.dueDate = dueDate;
    }

    @Builder
    public WeeklyPlanetResponseDto(Long todoListId, String dueDate, Byte planetType,
                                   Short planetSize, Byte planetColor, Byte planetLevel) {
        this.todoListId = todoListId;
        this.dueDate = dueDate;
        this.planetType = planetType;
        this.planetSize = planetSize;
        this.planetColor = planetColor;
        this.planetLevel = planetLevel;
    }
}
