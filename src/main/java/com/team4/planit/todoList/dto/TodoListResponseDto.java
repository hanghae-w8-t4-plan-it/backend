package com.team4.planit.todoList.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoListResponseDto {
    private String dueDate;
    private Integer unAchievedTodoCnt;
    private Byte planetType;
    private Short planetSize;
    private Byte planetColor;
    private Byte planetLevel;


    public TodoListResponseDto(String dueDate, Integer unAchievedTodoCnt) {
        this.dueDate = dueDate;
        this.unAchievedTodoCnt = unAchievedTodoCnt;
    }

    @Builder
    public TodoListResponseDto(String dueDate, Byte planetType, Short planetSize, Byte planetColor, Byte planetLevel) {
        this.dueDate = dueDate;
        this.planetType = planetType;
        this.planetSize = planetSize;
        this.planetColor = planetColor;
        this.planetLevel = planetLevel;
    }
}
