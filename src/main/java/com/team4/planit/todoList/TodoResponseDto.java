package com.team4.planit.todoList;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoResponseDto {

    private Long todoListId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memo;

    private String dueDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isAchieved;

    @Builder
    public TodoResponseDto(Long todoListId, String title, String dueDate, String memo, Boolean isAchieved) {
        this.todoListId = todoListId;
        this.title = title;
        this.memo = memo;
        this.dueDate = dueDate;
        this.isAchieved = isAchieved;
    }
}
