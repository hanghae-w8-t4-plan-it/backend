package com.team4.planit.statistic.achievement;

import com.team4.planit.member.Member;
import com.team4.planit.statistic.StatisticPeriodCode;
import com.team4.planit.todo.Todo;
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
    public void createAchievement(Member member, Todo todo) {
        Integer achievementCnt = todoRepository.countAllByDueDateAndIsAchieved(todo, true);
        Integer unAchievementCnt = todoRepository.countAllByDueDateAndIsAchieved(todo, false);
        Achievement achievement = Achievement.builder()
                .member(member)
                .period(StatisticPeriodCode.DAY)
                .achievementRate(Float.parseFloat(String.format("%.1f", ((float) achievementCnt / (achievementCnt + unAchievementCnt) * 100))))
                .achievementCnt(achievementCnt)
                .startDate(todo.getDueDate())
                .build();
        achievementRepository.save(achievement);
    }
}
