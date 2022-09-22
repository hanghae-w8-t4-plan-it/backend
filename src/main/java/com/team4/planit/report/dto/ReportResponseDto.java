package com.team4.planit.report.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReportResponseDto {

    private List<String> categoryRank;
    private List<String> achievementCountTop;
    private List<String> concentrationTimeTop;
    private Integer monthlyTotalLikes;
    private MostLikeResponseDto mostLikeDates;
    private MostLikeResponseDto mostLikeMembers;

    @Builder
    public ReportResponseDto(List<String> categoryRank, List<String> achievementCountTop,
                             List<String> concentrationTimeTop, Integer monthlyTotalLikes,
                             MostLikeResponseDto mostLikeDates, MostLikeResponseDto mostLikeMembers) {
        this.categoryRank = categoryRank;
        this.achievementCountTop = achievementCountTop;
        this.concentrationTimeTop = concentrationTimeTop;
        this.monthlyTotalLikes = monthlyTotalLikes;
        this.mostLikeDates = mostLikeDates;
        this.mostLikeMembers = mostLikeMembers;
    }

}
