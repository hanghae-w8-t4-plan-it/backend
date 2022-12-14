package com.team4.planit.category.dto;

import com.team4.planit.category.CategoryStatusCode;
import com.team4.planit.todo.dto.TodoResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryDetailResponseDto {
    private Long todoListId;
    private Long categoryId;
    private String categoryName;
    private String categoryColor;
    private Boolean isPublic;
    private CategoryStatusCode categoryStatus;
    private List<TodoResponseDto> todos;

    @Builder
    public CategoryDetailResponseDto(Long todoListId, Long categoryId, String categoryName, String categoryColor,
                                     Boolean isPublic, CategoryStatusCode categoryStatus, List<TodoResponseDto> todos) {
        this.todoListId = todoListId;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.isPublic = isPublic;
        this.categoryStatus = categoryStatus;
        this.todos = todos;
    } 
}
