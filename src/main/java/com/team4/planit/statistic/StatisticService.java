package com.team4.planit.statistic;

import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.statistic.achievement.Achievement;
import com.team4.planit.statistic.achievement.AchievementRepository;
import com.team4.planit.statistic.concentration.Concentration;
import com.team4.planit.statistic.concentration.ConcentrationRepository;
import com.team4.planit.statistic.dto.AchievementRateResponseDto;
import com.team4.planit.statistic.dto.ConcentrationRateResponseDto;
import com.team4.planit.statistic.dto.StatisticDayResponseDto;
import com.team4.planit.statistic.dto.StatisticPeriodResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final Check check;
    private final AchievementRepository achievementRepository;
    private final ConcentrationRepository concentrationRepository;

    @Transactional
    public StatisticDayResponseDto getStatisticDay(String date, HttpServletRequest request) {
        Member member = check.validateMember(request);
        List<Concentration> concentrations = concentrationRepository.findAllByMemberAndStartDateAndDay(member, date);
        List<ConcentrationRateResponseDto> concentrationRateResponseDtoList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            concentrationRateResponseDtoList.add(new ConcentrationRateResponseDto(String.format("%02d", i)));
        }
        for (Concentration concentration : concentrations) {
            concentrationRateResponseDtoList.set(Integer.parseInt(concentration.getStartDate().substring(11, 13)), ConcentrationRateResponseDto.builder()
                    .concentrationRate(concentration.getConcentrationRate())
                    .startDate(concentration.getStartDate())
                    .build()
            );
        }
        Achievement achievement = achievementRepository.findAllByMemberAndStartDateAndPeriod(member, date, "Day").orElseGet(
                Achievement.builder()
                        .member(member)
                        .period("Day")
                        .achievementRate(0f)
                        .achievementCnt(0)
                        .todoCnt(0)
                        .startDate(date)
                        .todoCnt(0)::build
        );
        achievementRepository.save(achievement);
        return StatisticDayResponseDto.builder()
                .memberId(member.getMemberId())
                .achievementRate(achievement.getAchievementRate())
                .achievementCnt(achievement.getAchievementCnt())
                .achievementTotalTodoCnt(achievement.getTodoCnt())
                .concentrationRates(concentrationRateResponseDtoList)
                .build();
    }

    @Transactional
    public StatisticPeriodResponseDto getStatisticPeriod(String period,
                                                         String startDate,
                                                         HttpServletRequest request) throws ParseException {
        Member member = check.validateMember(request);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(sdf.parse(startDate));
        String[] periodName;
        String endDate = "2022-12-31";
        int periodCode = Calendar.DATE;
        int periodNum = 1;
        switch (period) {
            case "week":
                cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                endDate = sdf.format(cal.getTime());
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                startDate = sdf.format(cal.getTime());
                period = "DayTotal";
                periodName = new String[]{"월", "화", "수", "목", "금", "토", "일"};
                break;
            case "month":
                cal.set(Calendar.DAY_OF_MONTH, 1);
                cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                startDate = sdf.format(cal.getTime());
                cal.add(Calendar.DATE, 28);
                endDate = sdf.format(cal.getTime());
                period = "Week";
                periodName = new String[]{"1주", "2주", "3주", "4주", "5주"};
                periodNum = 7;
                break;
            case "year":
                cal.set(Integer.parseInt(startDate.substring(0, 4)), Calendar.JANUARY, 1);
                startDate = sdf.format(cal.getTime());
                cal.add(Calendar.DATE, 365);
                endDate = sdf.format(cal.getTime());
                period = "Month";
                periodName = new String[]{"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"};
                periodCode = Calendar.MONTH;
                break;
            default:
                period = "Month";
                periodName = new String[]{"1월"};
                break;
        }
        List<Concentration> concentrations = concentrationRepository.findAllByMemberAndPeriod(member.getMemberId(), period, startDate, endDate);
        List<Achievement> achievements = achievementRepository.findAllByMemberAndPeriod(member.getMemberId(), period, startDate, endDate);
        List<ConcentrationRateResponseDto> concentrationRateResponseDtoList = new ArrayList<>();
        List<AchievementRateResponseDto> achievementRateResponseDtoList = new ArrayList<>();
        cal.setTime(sdf.parse(startDate));
        for (int i = 0; i < periodName.length; i++) {
            concentrationRateResponseDtoList.add(new ConcentrationRateResponseDto(periodName[i]));
            achievementRateResponseDtoList.add(new AchievementRateResponseDto(periodName[i]));
            for (Concentration concentration : concentrations) {
                if (concentration.getStartDate().equals(startDate)) {
                    concentrationRateResponseDtoList.set(i, ConcentrationRateResponseDto.builder()
                            .concentrationRate(concentration.getConcentrationRate())
                            .startDate(periodName[i])
                            .build()
                    );
                }
            }
            for (Achievement achievement : achievements) {
                if (achievement.getStartDate().equals(startDate)) {
                    achievementRateResponseDtoList.set(i, AchievementRateResponseDto.builder()
                            .achievementRate(achievement.getAchievementRate())
                            .startDate(periodName[i])
                            .build()
                    );
                }
            }
            cal.add(periodCode, periodNum);
            startDate = sdf.format(cal.getTime());
        }
        return new StatisticPeriodResponseDto(achievementRateResponseDtoList, concentrationRateResponseDtoList);
    }
}
