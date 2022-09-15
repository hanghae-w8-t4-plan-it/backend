package com.team4.planit.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MostLikeResponseDto {

    private Integer likesCount;
    private List<String> data;

    public MostLikeResponseDto(Integer likesCount, List<String> data) {
        this.likesCount = likesCount;
        this.data = data;
    }
}
