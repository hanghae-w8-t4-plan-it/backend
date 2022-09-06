package com.team4.planit.todo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoResponseDto {

    private Long todoListId;

    private Long todoId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memo;

    private String dueDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isAchieved;

    @Builder
    public TodoResponseDto(Long todoListId, Long todoId, String title, String dueDate, String memo, Boolean isAchieved) {
        this.todoListId = todoListId;
        this.todoId = todoId;
        this.title = title;
        this.memo = memo;
        this.dueDate = dueDate;
        this.isAchieved = isAchieved;
    }
}
