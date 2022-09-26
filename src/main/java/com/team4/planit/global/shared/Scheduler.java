package com.team4.planit.global.shared;

import com.team4.planit.member.Member;
import com.team4.planit.member.MemberRepository;
import com.team4.planit.statistic.achievement.Achievement;
import com.team4.planit.statistic.achievement.AchievementRepository;
import com.team4.planit.statistic.concentration.Concentration;
import com.team4.planit.statistic.concentration.ConcentrationRepository;
import com.team4.planit.timer.Timer;
import com.team4.planit.timer.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final MemberRepository memberRepository;

    private final ConcentrationRepository concentrationRepository;
    private final AchievementRepository achievementRepository;
    private final TimerRepository timerRepository;


    @Scheduled(cron = "0 50 3 * * *")
    public void updateStatistic() throws ParseException {
        List<Member> memberList = memberRepository.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(sdf.parse(String.valueOf(DateTime.now())));
        String startDate = sdf.format(cal.getTime());
        cal.add(Calendar.DATE,1);
        String endDate = sdf.format(cal.getTime());
        makeStatisticByPeriod(memberList,startDate,endDate,"DayTotal");
        //week
        cal.setTime(sdf.parse(String.valueOf(DateTime.now())));
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        startDate = sdf.format(cal.getTime());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        endDate = sdf.format(cal.getTime());
        makeStatisticByPeriod(memberList, startDate, endDate, "Week");
        //month
        cal.setTime(sdf.parse(String.valueOf(DateTime.now())));
        cal.set(Calendar.DAY_OF_MONTH, 1);
        startDate = sdf.format(cal.getTime());
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        endDate = sdf.format(cal.getTime());
        makeStatisticByPeriod(memberList, startDate, endDate, "Month");
    }

    private void makeStatisticByPeriod(List<Member> memberList, String startDate, String endDate, String period) {
        for (Member member : memberList) {
            //집중도 구하기 (%) 월요일부터 일요일
            Long memberId = member.getMemberId();
            List<Timer> timerList = timerRepository.findTimerDuringPeriod(memberId, startDate, endDate);
            int totalElapsedTime = 0;
            int totalSetTime = 0;
            for (Timer timer : timerList) {
                totalElapsedTime += timer.getElapsedTime();
                totalSetTime += timer.getSetTime();
            }
            float elapsed = totalElapsedTime;
            float concentrationRate;
            concentrationRate = 0;
            if (totalSetTime != 0) {
                concentrationRate = Float.parseFloat(String.format("%.1f", ((elapsed / totalSetTime) * 100)));
                int finalTotalElapsedTime = totalElapsedTime;
                float finalConcentrationRate = concentrationRate;
                Concentration concentration =
                        concentrationRepository.findConcentrationByMemberAndStartDateAndPeriod(member, startDate, period)
                                .orElseGet(() -> new Concentration(member, period, finalConcentrationRate, finalTotalElapsedTime, startDate));
                concentration.update(member, period, concentrationRate, totalElapsedTime, startDate);
                concentrationRepository.save(concentration);
            }
            //달성률 구하기 (%,개)
            List<Achievement> achievementList = achievementRepository.findAllByMemberDuringPeriod(memberId, startDate, endDate);
            int totalAchievementCnt = 0;
            int totalAchievementTodoCnt = 0;
            for (Achievement achievement : achievementList) {
                totalAchievementCnt += achievement.getAchievementCnt();
                totalAchievementTodoCnt += achievement.getTodoCnt();
            }
            float achieved = totalAchievementCnt;
            float achievementRate;
            achievementRate = 0;
            if (totalAchievementTodoCnt != 0) {
                achievementRate = Float.parseFloat(String.format("%.1f", (achieved / totalAchievementTodoCnt * 100)));
                float finalAchievementRate = achievementRate;
                int finalTotalAchievementCnt = totalAchievementCnt;
                int finalTotalAchievementTodoCnt = totalAchievementTodoCnt;
                Achievement achievement = achievementRepository.findAllByMemberAndStartDateAndPeriod(member, startDate, period)
                        .orElseGet(() -> new Achievement(member, period, finalAchievementRate, finalTotalAchievementCnt, startDate, finalTotalAchievementTodoCnt));
                achievement.update(achievementRate, totalAchievementCnt, totalAchievementTodoCnt);
                achievementRepository.save(achievement);
            }
        }
    }
}
