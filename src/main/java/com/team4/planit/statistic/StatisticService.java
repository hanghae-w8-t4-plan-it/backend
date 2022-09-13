package com.team4.planit.statistic;

import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final Check check;
    public ResponseEntity<?> getStatisticDay(Long memberId, HttpServletRequest request) {
        Member member = check.validateMember(request);
    }

    public ResponseEntity<?> getStatisticWeek(Long memberId, HttpServletRequest request) {
    }

    public ResponseEntity<?> getStatisticMonth(Long memberId, HttpServletRequest request) {
    }

    public ResponseEntity<?> getStatisticYear(Long memberId, HttpServletRequest request) {
    }
}
