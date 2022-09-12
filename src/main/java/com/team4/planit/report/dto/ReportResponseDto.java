package com.team4.planit.report.dto;

import com.team4.planit.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReportResponseDto {

    private List<Category> categoryRank;

    @Builder
    public ReportResponseDto(List<Category> categoryRank) {
        this.categoryRank = categoryRank;
    }
}
