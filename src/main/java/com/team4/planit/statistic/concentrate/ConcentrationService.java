package com.team4.planit.statistic.concentrate;

import com.team4.planit.member.Member;
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
        Float lastHour = Float.parseFloat(timer.getLastDate().substring(11, 13));
        Float lastMinute = Float.parseFloat(timer.getLastDate().substring(14, 16));
        if (!startHour.equals(lastHour)) {
            Concentration concentrationFirst = Concentration.builder()
                    .member(member)
                    .period(ConcentrationPeriodCode.DAY)
                    .concentrationRate(100f)
                    .concentrationTime(Math.round(startHour))
                    .startDate(timer.getStartDate())
                    .build();
            concentrationRepository.save(concentrationFirst);
            if (lastHour > startHour) {
                for (int i = 1; i <= lastHour - startHour; i++) {
                    if (lastHour.equals(startHour + i)) break;
                    Concentration concentrationMiddle = Concentration.builder()
                            .member(member)
                            .period(ConcentrationPeriodCode.DAY)
                            .concentrationRate(100f)
                            .concentrationTime(Math.round(startHour + i))
                            .startDate(timer.getStartDate())
                            .build();
                    concentrationRepository.save(concentrationMiddle);
                }
            } else if (lastHour < startHour) {
                for (int i = 1; i <= lastHour + 24 - startHour; i++) {
                    if (lastHour + 24 == (startHour + i)) break;
                    Concentration concentrationMiddle = Concentration.builder()
                            .member(member)
                            .period(ConcentrationPeriodCode.DAY)
                            .concentrationRate(100f)
                            .concentrationTime(Math.round(startHour + i))
                            .startDate(timer.getStartDate())
                            .build();
                    concentrationRepository.save(concentrationMiddle);
                }
            }
            Concentration concentrationLast = Concentration.builder()
                    .member(member)
                    .period(ConcentrationPeriodCode.DAY)
                    .concentrationRate(Float.parseFloat(String.format("%.1f", ((lastMinute - (timer.getRemainTime()%60)) / lastMinute * 100))))
                    .concentrationTime(Math.round(lastHour))
                    .startDate(timer.getStartDate())
                    .build();
            concentrationRepository.save(concentrationLast);
        } else {
            Concentration concentration = Concentration.builder()
                    .member(member)
                    .period(ConcentrationPeriodCode.DAY)
                    .concentrationRate(Float.parseFloat(String.format("%.1f", ((float) (timer.getSetTime() - timer.getRemainTime()) / timer.getSetTime() * 100))))
                    .concentrationTime(Math.round(startHour))
                    .startDate(timer.getStartDate())
                    .build();
            concentrationRepository.save(concentration);
        }
    }
}