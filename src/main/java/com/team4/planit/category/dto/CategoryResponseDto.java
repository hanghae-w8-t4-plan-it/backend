package com.team4.planit.category.dto;

import com.team4.planit.category.CategoryStatusCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private Long categoryId;
    private String categoryName;
    private Boolean isPublic;
    private String categoryColor;
    private CategoryStatusCode categoryStatus;
    private Boolean isEmpty;

    @Builder
    public CategoryResponseDto(Long categoryId, String categoryName, Boolean isPublic, String categoryColor,
                               CategoryStatusCode categoryStatus, Long todoCnt) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.isPublic = isPublic;
        this.categoryColor = categoryColor;
        this.categoryStatus = categoryStatus;
        this.isEmpty = todoCnt == 0;
    }
}
