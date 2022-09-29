package com.team4.planit.todo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.team4.planit.statistic.achievement.AchievementResponseDto;
import com.team4.planit.todo.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoResponseSupportDto {

    private Long todoListId;

    private Long todoId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String memo;

    private String dueDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean isAchieved;

    private AchievementResponseDto achievementStatus;

    public TodoResponseSupportDto(Todo todo, AchievementResponseDto achievementStatus) {
        this.todoListId = todo.getTodoList().getTodoListId();
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.memo = todo.getMemo();
        this.dueDate = todo.getDueDate();
        this.isAchieved = todo.getIsAchieved();
        this.achievementStatus = achievementStatus;
    }
}
