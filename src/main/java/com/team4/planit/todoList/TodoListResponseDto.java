package com.team4.planit.todoList;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoListResponseDto {
    private String dueDate;
    private Integer unAchievedTodoCnt;

    public TodoListResponseDto(String dueDate, Integer unAchievedTodoCnt) {
        this.dueDate = dueDate;
        this.unAchievedTodoCnt = unAchievedTodoCnt;
    }
}
