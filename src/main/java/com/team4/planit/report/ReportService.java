package com.team4.planit.report;

import com.team4.planit.category.CategoryRepositorySupport;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.report.dto.MostConcentrationTimeResponseDto;
import com.team4.planit.report.dto.MostLikeResponseDto;
import com.team4.planit.report.dto.ReportResponseDto;
import com.team4.planit.statistic.achievement.AchievementRepository;
import com.team4.planit.statistic.concentration.ConcentrationRepositorySupport;
import com.team4.planit.timer.TimerRepository;
import com.team4.planit.todoList.like.LikesRepository;
import com.team4.planit.todoList.like.LikesRepositorySupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final Check check;
    private final CategoryRepositorySupport categoryRepositorySupport;
    private final LikesRepository likesRepository;
    private final LikesRepositorySupport likesRepositorySupport;
    private final AchievementRepository achievementRepository;
    private final ConcentrationRepositorySupport concentrationRepositorySupport;
    private final TimerRepository timerRepository;

    public ReportResponseDto getReport(String month, HttpServletRequest request) {
        Member member = check.validateMember(request);
        List<String> categoryRank = categoryRepositorySupport.findAllCategoryRank(member, month);
        List<String> achievementCountTop = achievementRepository.findAchievementCountTop(member.getMemberId(), month);
        List<String> concentrationTimeTop = timerRepository.findConcentrationTimeTop(member.getMemberId(), month);
        MostConcentrationTimeResponseDto mostConcentrationTime = concentrationRepositorySupport.findMostConcentrationTime(member, month);
        Integer monthlyTotalLikes = likesRepositorySupport.findMonthlyTotalLikes(member, month);
        List<String> topLikeDates = likesRepository.findTopLikeDates(member.getMemberId(), month);
        List<String> topLikeMembers = likesRepository.findTopLikeMembers(member.getMemberId(), month);
        List<String> achievementCombo = achievementRepository.findAllByMemberAndStartDate(member.getMemberId(), month);
        getMaxCombo(achievementCombo);
        return ReportResponseDto.builder()
                .categoryRank(categoryRank)
                .achievementCountTop(achievementCountTop)
                .concentrationTimeTop(concentrationTimeTop)
                .mostConcentrationTime(mostConcentrationTime)
                .monthlyTotalLikes(monthlyTotalLikes)
                .mostLikeDates(makeMostLikeResponseDto(topLikeDates))
                .mostLikeMembers(makeMostLikeResponseDto(topLikeMembers))
                .build();
    }

    private void getMaxCombo(List<String> achievementCombo) {
        int achievementCnt = 1;
        List<Integer> cntList = new ArrayList<>();
        if (achievementCombo.size() == 0) {
            cntList.add(0);
        }
        for (int i = 0; i < achievementCombo.size(); i++) {
            if(i > 0) {
                if ((getAnInt(achievementCombo.get(i - 1).substring(8, 10)) + 1) == getAnInt(achievementCombo.get(i).substring(8, 10))) {
                    achievementCnt += 1;
                }
                if ((getAnInt(achievementCombo.get(i - 1).substring(8, 10)) + 1) != getAnInt(achievementCombo.get(i).substring(8, 10))) {
                    cntList.add(achievementCnt);
                    achievementCnt = 1;
                }
                if (i + 1 == achievementCombo.size()) {
                    cntList.add(achievementCnt);
                }
            }
        }
        int max = Collections.max(cntList);
    }

    private MostLikeResponseDto makeMostLikeResponseDto(List<String> topLikeData) {
        List<String> data = new ArrayList<>();
        if (topLikeData.size() != 0) {
            Integer likesCount = getAnInt(topLikeData.get(0).split(",")[0]);
            for (String topLike : topLikeData) {
                String item = topLike.split(",")[1];
                data.add(item);
            }
            return new MostLikeResponseDto(likesCount, data);
        }
        return new MostLikeResponseDto(0, data);
    }

    private int getAnInt(String achievementCombo) {
        return Integer.parseInt(achievementCombo);
    }
}

