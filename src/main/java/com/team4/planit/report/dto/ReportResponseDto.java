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
    private MostLikeResponseDto mostLikeDates;
    private MostLikeResponseDto mostLikeMembers;

    @Builder
    public ReportResponseDto(List<String> categoryRank, Integer monthlyTotalLikes, MostLikeResponseDto mostLikeDates, MostLikeResponseDto mostLikeMembers) {
        this.categoryRank = categoryRank;
        this.monthlyTotalLikes = monthlyTotalLikes;
        this.mostLikeDates = mostLikeDates;
        this.mostLikeMembers = mostLikeMembers;
    }

}
