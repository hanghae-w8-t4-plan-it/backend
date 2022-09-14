package com.team4.planit.report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReportResponseDto {

    private List<String> categoryRank;
    private Integer monthlyTotalLikes;

    @Builder
    public ReportResponseDto(List<String> categoryRank, Integer monthlyTotalLikes) {
        this.categoryRank = categoryRank;
        this.monthlyTotalLikes = monthlyTotalLikes;
    }

}
