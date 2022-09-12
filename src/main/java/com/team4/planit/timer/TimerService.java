package com.team4.planit.timer;

import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import com.team4.planit.statistic.concentrate.ConcentrationService;
import com.team4.planit.timer.dto.TimerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createTimer(TimerRequestDto timerRequestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(timerRequestDto.getSetTime());
        LocalDateTime lastTime = LocalDateTime.now().minusMinutes(timerRequestDto.getRemainTime());
        Timer timer = Timer.builder()
                .member(member)
                .setTime(timerRequestDto.getSetTime())
                .remainTime(timerRequestDto.getRemainTime())
                .startDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(startTime))
                .lastDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(lastTime))
                .targetDate(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now()))
                .build();
        timerRepository.save(timer);
        concentrationService.createConcentrateRate(timer, member);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
