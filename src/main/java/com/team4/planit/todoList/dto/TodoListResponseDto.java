package com.team4.planit.todoList.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoListResponseDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long todoListId;
    private String dueDate;
    private Byte planetType;
    private Short planetSize;
    private Byte planetColor;
    private Byte planetLevel;


    public TodoListResponseDto(String dueDate) {
        this.dueDate = dueDate;
    }

    @Builder
    public TodoListResponseDto(Long todoListId, String dueDate, Byte planetType,
                               Short planetSize, Byte planetColor, Byte planetLevel) {
        this.todoListId = todoListId;
        this.dueDate = dueDate;
        this.planetType = planetType;
        this.planetSize = planetSize;
        this.planetColor = planetColor;
        this.planetLevel = planetLevel;
    }
}
