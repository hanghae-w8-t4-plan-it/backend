package com.team4.planit.statistic;

import com.team4.planit.global.shared.Message;
import com.team4.planit.statistic.dto.StatisticDayResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic")
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping()
    public ResponseEntity<?> getStatisticDay(@RequestParam String date, HttpServletRequest request) {
        StatisticDayResponseDto statisticDayResponseDto = statisticService.getStatisticDay(date, request);
        return new ResponseEntity<>(Message.success(statisticDayResponseDto), HttpStatus.OK);
    }
}
