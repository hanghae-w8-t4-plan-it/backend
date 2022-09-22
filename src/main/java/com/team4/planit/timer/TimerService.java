package com.team4.planit.timer;

import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.statistic.concentration.ConcentrationService;
import com.team4.planit.timer.dto.TimerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class TimerService {
    private final Check check;
    private final ConcentrationService concentrationService;
    private final TimerRepository timerRepository;

    @Transactional
    public void createTimer(TimerRequestDto timerRequestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(timerRequestDto.getElapsedTime());
        Timer timer = Timer.builder()
                .member(member)
                .setTime(timerRequestDto.getSetTime())
                .elapsedTime(timerRequestDto.getElapsedTime())
                .startDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(startTime))
                .lastHour(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()))
                .lastDate(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDateTime.now()))
                .build();
        timerRepository.save(timer);
        concentrationService.createConcentration(timer, member);
    }
}
