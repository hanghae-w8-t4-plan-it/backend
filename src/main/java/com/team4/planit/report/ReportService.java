package com.team4.planit.report;

import com.team4.planit.category.CategoryRepositorySupport;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.report.dto.MostConcentrationTimeResponseDto;
import com.team4.planit.report.dto.MostLikeResponseDto;
import com.team4.planit.report.dto.ReportResponseDto;
import com.team4.planit.statistic.achievement.AchievementRepository;
import com.team4.planit.statistic.achievement.AchievementRepositorySupport;
import com.team4.planit.statistic.concentration.ConcentrationRepositorySupport;
import com.team4.planit.statistic.dto.AchievementCountTopResponseDto;
import com.team4.planit.timer.TimerRepositorySupport;
import com.team4.planit.timer.dto.ConcentrationTimeTopResponseDto;
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
    private final LikesRepositorySupport likesRepositorySupport;
    private final AchievementRepository achievementRepository;
    private final AchievementRepositorySupport achievementRepositorySupport;
    private final ConcentrationRepositorySupport concentrationRepositorySupport;
    private final TimerRepositorySupport timerRepositorySupport;

    public ReportResponseDto getReport(String month, HttpServletRequest request) {
        Member member = check.validateMember(request);
        List<String> categoryRank = categoryRepositorySupport.findAllCategoryRank(member, month);
        Integer maxAchievementCount = achievementRepositorySupport.findMaxAchievementCount(member, month);
        List<String> achievementCountTop = achievementRepositorySupport.findAchievementCountTop(member, month, maxAchievementCount);
        Integer maxSumElapsedTime = timerRepositorySupport.findMaxSumElapsedTime(member, month);
        List<String> concentrationTimeTop = timerRepositorySupport.findConcentrationTimeTop(member, month, maxSumElapsedTime);
        MostConcentrationTimeResponseDto mostConcentrationTime = concentrationRepositorySupport.findMostConcentrationTime(member, month);
        Integer monthlyTotalLikes = likesRepositorySupport.findMonthlyTotalLikes(member, month);
        Long maxLikesCountByTodoList = likesRepositorySupport.findMaxLikesCountByTodoList(member, month);
        List<String> topLikeDates = likesRepositorySupport.findTopLikeDates(member, month, maxLikesCountByTodoList);
        Long maxLikesCountByMember = likesRepositorySupport.findMaxLikesCountByMember(member, month);
        List<String> topLikeMembers = likesRepositorySupport.findTopLikeMembers(member, month, maxLikesCountByMember);
        List<String> achievementCombo = achievementRepository.findAllByMemberAndStartDate(member.getMemberId(), month);
        getMaxCombo(achievementCombo);

        return ReportResponseDto.builder()
                .categoryRank(categoryRank)
                .achievementCountTop(new AchievementCountTopResponseDto(maxAchievementCount, achievementCountTop))
                .concentrationTimeTop(new ConcentrationTimeTopResponseDto(maxSumElapsedTime, concentrationTimeTop))
                .mostConcentrationTime(mostConcentrationTime)
                .achievementCombo(getMaxCombo(achievementCombo))
                .monthlyTotalLikes(monthlyTotalLikes)
                .mostLikeDates(new MostLikeResponseDto(maxLikesCountByTodoList, topLikeDates))
                .mostLikeMembers(new MostLikeResponseDto(maxLikesCountByMember, topLikeMembers))
                .build();
    }

    private Integer getMaxCombo(List<String> achievementCombo) {
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
        return Collections.max(cntList);
    }

    private int getAnInt(String achievementCombo) {
        return Integer.parseInt(achievementCombo);
    }
}

