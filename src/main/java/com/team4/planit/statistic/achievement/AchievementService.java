package com.team4.planit.statistic.achievement;

import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.member.Member;
import com.team4.planit.todo.Todo;
import com.team4.planit.todo.TodoRepository;
import com.team4.planit.todoList.TodoList;
import com.team4.planit.todoList.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AchievementService {
    private final AchievementRepository achievementRepository;
    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;

    @Transactional
    public void updateAchievement(Member member, String dueDate) {
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, dueDate)
                .orElseThrow(() -> new CustomException(ErrorCode.TODO_LIST_NOT_FOUND));
        List<Todo> todos = todoRepository.findAllByTodoList(todoList);
        int todoCnt = todos.size();
        Integer achievementCnt = 0;
        for (Todo todo : todos) {
            if (todo.getIsAchieved()) achievementCnt++;
        }
        Integer finalAchievementCnt = achievementCnt;
        Achievement achievement = achievementRepository.findAllByStartDateAndMember(dueDate, member)
                .orElseGet(() -> Achievement.builder()
                        .member(member)
                        .period("Day")
                        .achievementRate((float) 0.0)
                        .achievementCnt(finalAchievementCnt)
                        .startDate(dueDate)
                        .build());
        achievementRepository.save(achievement);
        float achievementRate = 0;
        if (todoCnt != 0) {
            achievementRate = Float.parseFloat(String.format("%.1f", ((float) achievementCnt / (todoCnt) * 100)));
            achievement.update(achievementRate, achievementCnt, todoCnt);
        } else achievement.update(achievementRate, achievementCnt, todoCnt);
        if (achievementCnt>5) todoList.setPlanetLevel((byte) 2);
        if (achievementCnt>10) todoList.setPlanetLevel((byte) 3);

    }
}
