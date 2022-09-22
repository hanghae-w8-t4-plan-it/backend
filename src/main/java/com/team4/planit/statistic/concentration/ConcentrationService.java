package com.team4.planit.statistic.concentration;

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
    public void createConcentration(Timer timer, Member member) {
        String start = timer.getStartDate();
        String last = timer.getLastHour();
        String date = timer.getLastDate();
        String startDate = start.substring(0, 11);
        float startHour = Float.parseFloat((start.substring(start.length() - 5, start.length() - 3)));
        float startMinute = Float.parseFloat((start.substring(start.length() - 2)));
        float lastHour = Float.parseFloat((last.substring(last.length() - 5, last.length() - 3)));
        float lastMinute = Float.parseFloat((last.substring(last.length() - 2)));
        if (!startDate.equals(date)) {
            if (lastHour != startHour) {
                while (startHour < 24) {
                    lastMinute(member, (60 - startMinute), startDate, (int) startHour);
                    startHour += 1;
                    startMinute = 0;
                }
                startHour = 0;
                while (lastHour - startHour > 0) {
                    lastMinute(member, lastMinute, date, (int) lastHour);
                    lastMinute = 60;
                    lastHour -= 1;

                }
            }
        }
        if (lastHour != startHour) {
            while (lastHour - startHour > 0) {
                lastMinute(member, lastMinute, date, (int) lastHour);
                lastMinute = 60;
                lastHour -= 1;
            }
            lastMinute(member, (60 - startMinute), date, (int) lastHour);
            return;
        }
        lastMinute(member, (lastMinute - startMinute), date, (int) lastHour);
    }

    private void lastMinute(Member member, float lastMinute, String date, int lastHour) {
        Concentration concentration = Concentration.builder()
                .member(member)
                .period("Day")
                .concentrationRate(Float.parseFloat(String.format("%.1f", (lastMinute / 60 * 100))))
                .concentrationTime((int) lastMinute)
                .startDate(date + " " + String.format("%02d", lastHour))
                .build();
        concentrationRepository.save(concentration);
    }
}