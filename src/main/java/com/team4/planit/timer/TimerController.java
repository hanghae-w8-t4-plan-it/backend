package com.team4.planit.timer;

import com.team4.planit.timer.dto.TimerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class TimerController {
    private final TimerService timerService;

    @PostMapping("/timer")
    public ResponseEntity<?> createTimer(@RequestBody TimerRequestDto timerRequestDto, HttpServletRequest request) {
        return timerService.createTimer(timerRequestDto, request);
    }
}
