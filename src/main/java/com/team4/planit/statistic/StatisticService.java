package com.team4.planit.statistic;

import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.statistic.achievement.Achievement;
import com.team4.planit.statistic.achievement.AchievementRepository;
import com.team4.planit.statistic.concentration.Concentration;
import com.team4.planit.statistic.concentration.ConcentrationRepository;
import com.team4.planit.statistic.dto.ConcentrationResponseDto;
import com.team4.planit.statistic.dto.StatisticDayResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final Check check;
    private final AchievementRepository achievementRepository;
    private final ConcentrationRepository concentrationRepository;

    public StatisticDayResponseDto getStatisticDay(String date, HttpServletRequest request) {
        Member member = check.validateMember(request);
        List<Concentration> concentrations = concentrationRepository.findAllByMemberAndStartDate(member.getMemberId(), date);
        List<ConcentrationResponseDto> concentrationResponseDtoList = new ArrayList<>();
        for(Concentration concentration : concentrations) {
            concentrationResponseDtoList.add(
                    ConcentrationResponseDto.builder()
                            .concentrationRate(concentration.getConcentrationRate())
                            .startDate(concentration.getStartDate())
                            .build()
            );
        }
        Achievement achievement = achievementRepository.findAllByMemberAndStartDate(member, date).orElse(null);
        return StatisticDayResponseDto.builder()
                .memberId(member.getMemberId())
                .achievementRate(achievement.getAchievementRate())
                .achievementCnt(achievement.getAchievementCnt())
                .concentrationRates(concentrationResponseDtoList)
                .build();
    }
}
