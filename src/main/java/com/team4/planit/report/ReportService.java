package com.team4.planit.report;

import com.team4.planit.category.CategoryRepositorySupport;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.report.dto.MostLikeResponseDto;
import com.team4.planit.report.dto.ReportResponseDto;
import com.team4.planit.todoList.like.LikesRepository;
import com.team4.planit.todoList.like.LikesRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final Check check;
    private final CategoryRepositorySupport categoryRepositorySupport;
    private final LikesRepository likesRepository;
    private final LikesRepositorySupport likesRepositorySupport;

    public ReportResponseDto getReport(String month, HttpServletRequest request) {
        Member member = check.validateMember(request);
        List<String> categoryRank = categoryRepositorySupport.findAllCategoryRank(member, month);
        Integer monthlyTotalLikes = likesRepositorySupport.findMonthlyTotalLikes(member, month);
        List<String> topLikeDates = likesRepository.findTopLikeDates(member.getMemberId(), month);
        List<String> topLikeMembers = likesRepository.findTopLikeMembers(member.getMemberId(), month);

        return ReportResponseDto.builder()
                .categoryRank(categoryRank)
                .monthlyTotalLikes(monthlyTotalLikes)
                .mostLikeDates(makeMostLikeResponseDto(topLikeDates))
                .mostLikeMembers(makeMostLikeResponseDto(topLikeMembers))
                .build();
    }

    private MostLikeResponseDto makeMostLikeResponseDto(List<String> topLikeData) {
        Integer likesCount = Integer.parseInt(topLikeData.get(0).split(",")[0]);
        List<String> data = new ArrayList<>();
        for (String topLike : topLikeData) {
            String item = topLike.split(",")[1];
            data.add(item);
        }
        return new MostLikeResponseDto(likesCount, data);
    }
}

