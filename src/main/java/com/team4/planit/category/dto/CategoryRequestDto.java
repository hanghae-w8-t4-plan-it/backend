package com.team4.planit.category.dto;

import com.team4.planit.category.CategoryStatusCode;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequestDto {
    private String categoryName;
    private String categoryColor;
    private Boolean isPublic;
    private CategoryStatusCode categoryStatus;

    @Builder
    public CategoryRequestDto(String categoryName, String categoryColor, Boolean isPublic, CategoryStatusCode categoryStatus) {
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.isPublic = isPublic;
        this.categoryStatus = categoryStatus;
    }
}
