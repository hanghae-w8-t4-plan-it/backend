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
    public void createConcentration(Timer timer, Member member) {
        String start = timer.getStartDate();
        String last = timer.getLastDate();
        String date = timer.getLastDate().substring(0,11);
        float startHour = Float.parseFloat((start.substring(start.length() - 5, start.length() - 3)));
        float startMinute = Float.parseFloat((start.substring(start.length() - 2)));
        float lastHour = Float.parseFloat((last.substring(last.length() - 5, last.length() - 3)));
        float lastMinute = Float.parseFloat((last.substring(last.length() - 2)));
        if(!start.substring(0,11).equals(date)){
            String startDate=start.substring(0,11);
            if(lastHour!=startHour) {
                while (startHour<24) {
                    Concentration concentrationLast = Concentration.builder()
                            .member(member)
                            .period(StatisticPeriodCode.DAY.getName())
                            .concentrationRate(Float.parseFloat(String.format("%.1f", ((60 - startMinute) / 60 * 100))))
                            .concentrationTime((int) (60 - startMinute))
                            .startDate(startDate + String.format("%02d",(int)startHour))
                            .build();
                    concentrationRepository.save(concentrationLast);
                    startHour += 1;
                    startMinute=0;
                }
                startHour=0;
                while (lastHour - startHour > 0) {
                    Concentration concentration = Concentration.builder()
                            .member(member)
                            .period(StatisticPeriodCode.DAY.getName())
                            .concentrationRate(Float.parseFloat(String.format("%.1f", (lastMinute / 60 * 100))))
                            .concentrationTime((int)lastMinute)
                            .startDate(date + String.format("%02d",(int)lastHour))
                            .build();
                    concentrationRepository.save(concentration);
                    lastMinute=60;
                    lastHour -=1;

                }
            }
        }
        if(lastHour!=startHour) {
            while (lastHour - startHour > 0) {
                Concentration concentration = Concentration.builder()
                        .member(member)
                        .period(StatisticPeriodCode.DAY.getName())
                        .concentrationRate(Float.parseFloat(String.format("%.1f", (lastMinute / 60 * 100))))
                        .concentrationTime((int)lastMinute)
                        .startDate(date + String.format("%02d",(int)lastHour))
                        .build();
                concentrationRepository.save(concentration);
                lastMinute=60;
                lastHour -=1;
            }
            Concentration concentrationLast = Concentration.builder()
                    .member(member)
                    .period(StatisticPeriodCode.DAY.getName())
                    .concentrationRate(Float.parseFloat(String.format("%.1f", ((60-startMinute) / 60 * 100))))
                    .concentrationTime((int)(60-startMinute ))
                    .startDate(date+String.format("%02d",(int)lastHour))
                    .build();
            concentrationRepository.save(concentrationLast);
            return;
        }
        Concentration concentrationFirst = Concentration.builder()
                .member(member)
                .period(StatisticPeriodCode.DAY.getName())
                .concentrationRate(Float.parseFloat(String.format("%.1f", ((lastMinute - startMinute) / 60 * 100))))
                .concentrationTime((int)(lastMinute - startMinute))
                .startDate(date+ String.format("%02d",(int)lastHour))
                .build();
        concentrationRepository.save(concentrationFirst);
    }
}