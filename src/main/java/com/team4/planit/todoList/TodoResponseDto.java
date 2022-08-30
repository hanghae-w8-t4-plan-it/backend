package com.team4.planit.todoList;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memo;

    public TodoResponseDto(String title) {
        this.title = title;
    }

    public TodoResponseDto(String title, String memo) {
        this.title = title;
        this.memo = memo;
    }
}
