package com.team4.planit.category;

import com.team4.planit.todoList.Todo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private Long categoryId;
    private String categoryName;
    private String categoryColor;
    private Boolean isPublic;
    private CategoryStatusCode categoryStatus;
    private List<Todo> todos;

    @Builder
    public CategoryResponseDto(Long categoryId, String categoryName, String categoryColor,
                               Boolean isPublic, CategoryStatusCode categoryStatus, List<Todo> todos) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.isPublic = isPublic;
        this.categoryStatus = categoryStatus;
        this.todos = todos;
    }
}
