package com.team4.planit.report;

import com.team4.planit.category.CategoryRepositorySupport;
import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.like.LikesRepositorySupport;
import com.team4.planit.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final Check check;
    private final CategoryRepositorySupport categoryRepositorySupport;
    private final LikesRepositorySupport likesRepositorySupport;

    public ResponseEntity<?> getReport(String month, HttpServletRequest request) {
        Member member = check.validateMember(request);
        List<String> categoryRank = categoryRepositorySupport.findAllCategoryRank(member, month);
        Integer monthlyTotalLikes = likesRepositorySupport.findMonthlyTotalLikes(member, month);

        return new ResponseEntity<>(Message.success(monthlyTotalLikes), HttpStatus.OK);
    }
}

