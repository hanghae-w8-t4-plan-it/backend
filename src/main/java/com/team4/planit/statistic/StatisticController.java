package com.team4.planit.statistic;

import com.team4.planit.global.shared.Message;
import com.team4.planit.statistic.dto.StatisticDayResponseDto;
import com.team4.planit.statistic.dto.StatisticPeriodResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

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
    @Transactional
    @GetMapping("/{period}")
    public ResponseEntity<?> getStatisticPeriod(@PathVariable String period,
                                                @RequestParam String startDate,
                                                HttpServletRequest request) throws ParseException {
        StatisticPeriodResponseDto statisticPeriodResponseDto = statisticService.getStatisticPeriod(period, startDate, request);
        return new ResponseEntity<>(Message.success(statisticPeriodResponseDto), HttpStatus.OK);
    }
}
