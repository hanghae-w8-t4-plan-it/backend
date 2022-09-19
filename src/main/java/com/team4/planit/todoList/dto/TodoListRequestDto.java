package com.team4.planit.todoList.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoListRequestDto {
    String dueDate;
    Byte planetType;
    Short planetSize;
    Byte planetColor;

    public TodoListRequestDto(String dueDate, Byte planetType) {
        this.dueDate = dueDate;
        this.planetType = planetType;
    }

    public TodoListRequestDto(String dueDate, Short planetSize, Byte planetColor) {
        this.dueDate = dueDate;
        this.planetSize = planetSize;
        this.planetColor = planetColor;
    }
}
