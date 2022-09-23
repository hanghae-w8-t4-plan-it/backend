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
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final Check check;
    private final AchievementRepository achievementRepository;
    private final ConcentrationRepository concentrationRepository;

    @Transactional
    public StatisticDayResponseDto getStatisticDay(String date, HttpServletRequest request){
        Member member = check.validateMember(request);
        List<Concentration> concentrations = concentrationRepository.findAllByMemberAndStartDateAndDay(member.getMemberId(), date);
        List<ConcentrationResponseDto> concentrationResponseDtoList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            concentrationResponseDtoList.add(new ConcentrationResponseDto(date + " " + String.format("%02d", i)));
            for (Concentration concentration : concentrations) {
                if(Integer.parseInt(concentration.getStartDate().substring(11, 13)) == i) {
                    concentrationResponseDtoList.set(i, ConcentrationResponseDto.builder()
                                    .concentrationRate(concentration.getConcentrationRate())
                                    .startDate(concentration.getStartDate())
                                    .build()
                    );
                }
            }
        }
        Achievement achievement = achievementRepository.findAllByMemberAndStartDateAndPeriod(member, date, "Day").orElseGet(
                Achievement.builder()
                        .member(member)
                        .period("Day")
                        .achievementRate(0f)
                        .achievementCnt(0)
                        .startDate(date)
                        .todoCnt(0)::build
        );
        achievementRepository.save(achievement);
        return StatisticDayResponseDto.builder()
                .memberId(member.getMemberId())
                .achievementRate(achievement.getAchievementRate())
                .achievementCnt(achievement.getAchievementCnt())
                .concentrationRates(concentrationResponseDtoList)
                .build();
    }
}
