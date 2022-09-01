package com.team4.planit.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String categoryName;
    private String categoryColor;
    private Boolean isPublic;
    private CategoryStatusCode categoryStatus;

    @Builder
    public CategoryResponseDto(Long id, String categoryName, String categoryColor, Boolean isPublic, CategoryStatusCode categoryStatus) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.isPublic = isPublic;
        this.categoryStatus = categoryStatus;
    }
}
