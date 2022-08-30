package com.team4.planit.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryRequestDto {
    private String categoryName;
    private String categoryColor;
    private Boolean isPublic;
    private Boolean isEnd;

    @Builder
    public CategoryRequestDto(String categoryName, String categoryColor, Boolean isPublic, Boolean isEnd) {
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.isPublic = isPublic;
        this.isEnd = isEnd;
    }
}
