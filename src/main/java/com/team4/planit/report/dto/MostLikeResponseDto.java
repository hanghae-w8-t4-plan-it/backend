package com.team4.planit.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MostLikeResponseDto {

    private Long likesCount;
    private List<String> data;

    public MostLikeResponseDto(Long likesCount, List<String> data) {
        this.likesCount = likesCount;
        this.data = data;
    }
}
