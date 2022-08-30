package com.team4.planit.todoList;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequestDto {
    private String title;

    private String memo;

    public TodoRequestDto(String title) {
        this.title = title;
    }

    public TodoRequestDto(String title, String memo) {
        this.title = title;
        this.memo = memo;
    }
}
