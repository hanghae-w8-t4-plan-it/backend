package com.team4.planit.statistic;

import lombok.RequiredArgsConstructor;
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
        return statisticService.getStatisticDay(date, request);
    }
}
