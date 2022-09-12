package com.team4.planit.report;

import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import com.team4.planit.report.dto.ReportResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final Check check;


    public ResponseEntity<?> getReport(HttpServletRequest request) {
        Member member = check.validateMember(request);

        return new ResponseEntity<>(Message.success(ReportResponseDto.builder().build()), HttpStatus.OK);
    }
}
