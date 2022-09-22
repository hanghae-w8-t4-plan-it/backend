package com.team4.planit.todoList;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.member.Member;
import com.team4.planit.todoList.dto.DailyTodoListResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team4.planit.statistic.achievement.QAchievement.achievement;
import static com.team4.planit.todo.QTodo.todo;
import static com.team4.planit.todoList.QTodoList.todoList;
import static com.team4.planit.todoList.like.QLikes.likes;

@Repository
public class TodoListRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public TodoListRepositorySupport(JPAQueryFactory queryFactory) {
        super(TodoList.class);
        this.queryFactory = queryFactory;
    }

    public List<String> findUnAchievedDueDatesByMemberAndYearAndMonth(Member member, String year, String month) {
        return queryFactory
                .select(todoList.dueDate)
                .from(todoList)
                .innerJoin(todo)
                .on(todo.todoList.eq(todoList))
                .where(todoList.member.eq(member),
                        todoList.dueDate.substring(0, 7).eq(year + "-" + month),
                                todo.isAchieved.isFalse())
                .groupBy(todoList)
                .orderBy(todoList.dueDate.asc())
                .fetch();
    }

    public DailyTodoListResponseDto findDailyTodoListByMemberAndDueDate(Member member, String dueDate) {
        return queryFactory
                .select(Projections.constructor(
                        DailyTodoListResponseDto.class,
                        todoList.todoListId,
                        todoList.dueDate,
                        todoList.planetType,
                        todoList.planetSize,
                        todoList.planetColor,
                        todoList.planetLevel,
                        achievement.achievementCnt,
                        likes.count()
                ))
                .from(todoList)
                .leftJoin(achievement)
                .on(todoList.member.eq(achievement.member), todoList.dueDate.eq(achievement.startDate))
                .innerJoin(likes)
                .on(todoList.eq(likes.todoList))
                .where(todoList.member.eq(member), todoList.dueDate.eq(dueDate), achievement.period.eq("Day"))
                .groupBy(todoList.dueDate)
                .fetchOne();

//        select tl.member_id, tl.todo_list_id, tl.todo_list_due_date, a.achievement_cnt, count(l.likes_id) as likes_count from todo_list tl
//        left join achievement a
//        on tl.member_id = a.member_id and tl.todo_list_due_date = a.achievement_start_date
//        inner join likes l
//        on tl.todo_list_id = l.todo_list_id
//        where tl.member_id = 28 and tl.todo_list_due_date = '2022-09-21' and a.achievement_period = 'Day'
//        group by tl.todo_list_due_date

    }

    public List<TodoList> findWeeklyPlanet(Member member, String startDate, String endDate) {
        return queryFactory
                .selectFrom(todoList)
                .where(todoList.member.eq(member), todoList.dueDate.between(startDate, endDate))
                .orderBy(todoList.dueDate.asc())
                .fetch();
    }

    public Integer findWeeklyTotalAchievement(Member member, String startDate, String endDate) {
        return Math.toIntExact(queryFactory
                .select(todoList.count())
                .from(todoList)
                .innerJoin(todo)
                .on(todoList.eq(todo.todoList))
                .where(todoList.member.eq(member), todoList.dueDate.between(startDate, endDate))
                .orderBy(todoList.dueDate.asc())
                .fetchFirst());
    }

    public Integer findWeeklyTotalLikes(Member member, String startDate, String endDate) {
        return Math.toIntExact(queryFactory
                .select(todoList.count())
                .from(todoList)
                .innerJoin(likes)
                .on(todoList.eq(likes.todoList))
                .where(todoList.member.eq(member), todoList.dueDate.between(startDate, endDate))
                .orderBy(todoList.dueDate.asc())
                .fetchFirst());
    }

}