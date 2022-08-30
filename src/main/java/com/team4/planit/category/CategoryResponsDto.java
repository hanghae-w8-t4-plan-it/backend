package com.team4.planit.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponsDto {
    private Long id;
    private String categoryName;
    private String categoryColor;
    private Boolean isPublic;

    @Builder
    public CategoryResponsDto(Long id, String categoryName, String categoryColor, Boolean isPublic) {
        this.id = id;
        this.categoryName = categoryName;
        this.categoryColor = categoryColor;
        this.isPublic = isPublic;
    }
}
