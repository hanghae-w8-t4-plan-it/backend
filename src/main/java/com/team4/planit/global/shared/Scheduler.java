package com.team4.planit.global.shared;

import com.team4.planit.member.Member;
import com.team4.planit.member.MemberRepository;
import com.team4.planit.statistic.achievement.AchievementRepository;
import com.team4.planit.statistic.concentration.ConcentrationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Scheduler {
    private final MemberRepository memberRepository;

    private final ConcentrationRepository concentrationRepository;
    private final AchievementRepository achievementRepository;

    @Scheduled(cron = "0 50 3 * * *")
    public void updateStatistic() throws ParseException {
        List<Member> memberList = memberRepository.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date temp = sdf.parse(String.valueOf(DateTime.now()));
        Calendar cal = Calendar.getInstance();
        cal.setTime(temp);
        cal.get(Calendar.WEEK_OF_YEAR);
        String endDate = sdf.format(cal.getTime());
        for (Object member : memberList ) {
            //집중도 구하기 (%)


            //달성률 구하기 (%,개)


        }

    }
}
