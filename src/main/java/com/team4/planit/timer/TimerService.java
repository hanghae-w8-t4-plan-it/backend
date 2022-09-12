package com.team4.planit.timer;

import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
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
//    private final ConcentrateRateService concentrateRateService;
    private final TimerRepository timerRepository;

    @Transactional
    public ResponseEntity<?> createTimer(TimerRequestDto timerRequestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        LocalDateTime startTime = LocalDateTime.now().minusMinutes(timerRequestDto.getSetTime() - timerRequestDto.getRemainTime());
        Timer timer = Timer.builder()
                .member(member)
                .setTime(timerRequestDto.getSetTime())
                .remainTime(timerRequestDto.getRemainTime())
                .date(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(startTime))
                .build();
        timerRepository.save(timer);
//        concentrateRateService.createConcentrateRate(timer);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}

// 분데이터로만 받고
