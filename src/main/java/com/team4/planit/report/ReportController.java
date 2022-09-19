package com.team4.planit.report;

import com.team4.planit.global.shared.Message;
import com.team4.planit.report.dto.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/report")
    public ResponseEntity<?> getReport(@RequestParam String month, HttpServletRequest request) {
        ReportResponseDto reportResponseDto = reportService.getReport(month, request);
        return new ResponseEntity<>(Message.success(reportResponseDto), HttpStatus.OK);
    }
}
