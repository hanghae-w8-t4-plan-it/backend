package com.team4.planit.todoList;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memo;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isAchieved;

    public TodoResponseDto(String title) {
        this.title = title;
    }

    @Builder
    public TodoResponseDto(String title, String memo, Boolean isAchieved) {
        this.title = title;
        this.memo = memo;
        this.isAchieved = isAchieved;
    }
}