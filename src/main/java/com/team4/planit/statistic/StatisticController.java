package com.team4.planit.statistic;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statistic/{memberId}")
public class StatisticController {
    private final StatisticService statisticService;

    @GetMapping("/day")
    public ResponseEntity<?> getStatisticDay(@PathVariable Long memberId, HttpServletRequest request) {
        return statisticService.getStatisticDay(memberId, request);
    }

    @GetMapping("/week")
    public ResponseEntity<?> getStatisticWeek(@PathVariable Long memberId, HttpServletRequest request) {
        return statisticService.getStatisticWeek(memberId, request);
    }

    @GetMapping("/month")
    public ResponseEntity<?> getStatisticMonth(@PathVariable Long memberId, HttpServletRequest request) {
        return statisticService.getStatisticMonth(memberId, request);
    }

    @GetMapping("/year")
    public ResponseEntity<?> getStatisticYear(@PathVariable Long memberId, HttpServletRequest request) {
        return statisticService.getStatisticYear(memberId, request);
    }
}
