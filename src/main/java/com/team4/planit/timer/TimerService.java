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

@Service
@RequiredArgsConstructor
public class TimerService {
    private final Check check;
    private final TimerRepository timerRepository;

    @Transactional
    public ResponseEntity<?> createTimer(TimerRequestDto timerRequestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Timer timer = new Timer(timerRequestDto.getSetTime(), timerRequestDto.getRemainTime(), member);
        timerRepository.save(timer);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
