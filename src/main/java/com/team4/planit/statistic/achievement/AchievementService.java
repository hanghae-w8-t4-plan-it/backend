package com.team4.planit.statistic.achievement;

import com.team4.planit.member.Member;
import com.team4.planit.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public void updateAchievement(Member member, String dueDate) {
        Integer achievementCnt = todoRepository.countAllByDueDateAndIsAchieved(dueDate, true);
        Integer unAchievementCnt = todoRepository.countAllByDueDateAndIsAchieved(dueDate, false);
        Achievement achievement = achievementRepository.findAllByStartDateAndMember(dueDate, member).orElseGet(() ->
                Achievement.builder()
                        .member(member)
                        .period("Day")
                        .achievementRate((float)0.0)
                        .achievementCnt(achievementCnt)
                        .startDate(dueDate)
                        .build());
        achievementRepository.save(achievement);
        float achievementRate=0;
        if (achievementCnt+unAchievementCnt!=0) achievementRate = Float.parseFloat(String.format("%.1f", ((float) achievementCnt / (achievementCnt + unAchievementCnt) * 100)));
        achievement.update(achievementRate, achievementCnt);
    }
}
