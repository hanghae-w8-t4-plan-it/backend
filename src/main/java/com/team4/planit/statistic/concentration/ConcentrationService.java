package com.team4.planit.statistic.concentration;

import com.team4.planit.member.Member;
import com.team4.planit.statistic.StatisticPeriodCode;
import com.team4.planit.timer.Timer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConcentrationService {
    private final ConcentrationRepository concentrationRepository;

    @Transactional
    public void createConcentrateRate(Timer timer, Member member) {
        Float startHour = Float.parseFloat(timer.getStartDate().substring(11, 13));
        Float startMinute = Float.parseFloat(timer.getStartDate().substring(14, 16));
        Float lastHour = Float.parseFloat(timer.getLastDate().substring(11, 13));
        Float lastMinute = Float.parseFloat(timer.getLastDate().substring(14, 16));
        Float targetHour = Float.parseFloat(timer.getTargetDate().substring(11, 13));
        Float targetMinute = Float.parseFloat(timer.getTargetDate().substring(14, 16));
        if (!startHour.equals(lastHour)) {
            Concentration concentrationFirst = Concentration.builder()
                    .member(member)
                    .period(StatisticPeriodCode.DAY)
                    .concentrationRate(100f)
                    .concentrationTime(Math.round(startHour))
                    .startDate(timer.getStartDate())
                    .build();
            concentrationRepository.save(concentrationFirst);
            if (lastHour > startHour) {
                for (int i = 1; i < lastHour - startHour; i++) {
                    Concentration concentrationMiddle = Concentration.builder()
                            .member(member)
                            .period(StatisticPeriodCode.DAY)
                            .concentrationRate(100f)
                            .concentrationTime(Math.round(startHour + i))
                            .startDate(timer.getStartDate())
                            .build();
                    concentrationRepository.save(concentrationMiddle);
                }
            } else if (lastHour < startHour) {
                for (int i = 1; i <= lastHour + 23 - startHour; i++) {
                    float maxHour = 0;
                    if (startHour + i > 23) {
                        maxHour = startHour + i - 24;
                    } else {
                        maxHour = startHour + i;
                    }
                    Concentration concentrationMiddle = Concentration.builder()
                            .member(member)
                            .period(StatisticPeriodCode.DAY)
                            .concentrationRate(100f)
                            .concentrationTime(Math.round(maxHour))
                            .startDate(timer.getStartDate())
                            .build();
                    concentrationRepository.save(concentrationMiddle);
                }
            }
            if (lastHour.equals(targetHour)) {
                Concentration concentrationLast = Concentration.builder()
                        .member(member)
                        .period(StatisticPeriodCode.DAY)
                        .concentrationRate(Float.parseFloat(String.format("%.1f", (lastMinute / targetMinute * 100))))
                        .concentrationTime(Math.round(lastHour))
                        .startDate(timer.getStartDate())
                        .build();
                concentrationRepository.save(concentrationLast);
            } else {
                // 시작 시간 != 끝난 시간 != 목표 시간
                Concentration concentrationLast = Concentration.builder()
                        .member(member)
                        .period(StatisticPeriodCode.DAY)
                        .concentrationRate(Float.parseFloat(String.format("%.1f", (lastMinute / 60 * 100))))
                        .concentrationTime(Math.round(lastHour))
                        .startDate(timer.getStartDate())
                        .build();
                concentrationRepository.save(concentrationLast);
            }
        } else if (!startHour.equals(targetHour)) {
            // 시작 시간 = 끝난 시간 != 목표 시간
            Concentration concentration = Concentration.builder()
                    .member(member)
                    .period(StatisticPeriodCode.DAY)
                    .concentrationRate(Float.parseFloat(String.format("%.1f", ((timer.getSetTime() - timer.getRemainTime()) / (60 - startMinute) * 100))))
                    .concentrationTime(Math.round(startHour))
                    .startDate(timer.getStartDate())
                    .build();
            concentrationRepository.save(concentration);
        } else {
            //시작 시간 = 끝난 시간 = 목표 시간
            Concentration concentration = Concentration.builder()
                    .member(member)
                    .period(StatisticPeriodCode.DAY)
                    .concentrationRate(Float.parseFloat(String.format("%.1f", ((float) (timer.getSetTime() - timer.getRemainTime()) / timer.getSetTime() * 100))))
                    .concentrationTime(Math.round(startHour))
                    .startDate(timer.getStartDate())
                    .build();
            concentrationRepository.save(concentration);
        }
    }
}