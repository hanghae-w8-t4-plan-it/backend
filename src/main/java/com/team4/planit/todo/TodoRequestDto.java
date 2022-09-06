package com.team4.planit.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoRequestDto {
    private String title;

    private String memo;

    private String dueDate;

    private Boolean isAchieved;

    public TodoRequestDto(String title, String memo, String dueDate, Boolean isAchieved) {
        this.title = title;
        this.memo = memo;
        this.dueDate = dueDate;
        this.isAchieved = isAchieved;
    }
}
