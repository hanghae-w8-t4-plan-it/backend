package com.team4.planit.todoList;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<TodoList> getWeeklyPlanet(Member member, String startDate, String endDate) {
        return queryFactory
                .selectFrom(todoList)
                .where(todoList.member.eq(member), todoList.dueDate.between(startDate, endDate))
                .orderBy(todoList.todoListId.asc())
                .fetch();
    }

    public Integer getWeeklyTotalAchievement(Member member, String startDate, String endDate) {
        return Math.toIntExact(queryFactory
                .select(todoList.count())
                .from(todoList)
                .innerJoin(todo)
                .on(todoList.eq(todo.todoList))
                .where(todoList.member.eq(member), todoList.dueDate.between(startDate, endDate))
                .orderBy(todoList.todoListId.asc())
                .fetchFirst());
    }

    public Integer getWeeklyTotalLikes(Member member, String startDate, String endDate) {
        return Math.toIntExact(queryFactory
                .select(todoList.count())
                .from(todoList)
                .innerJoin(likes)
                .on(todoList.eq(likes.todoList))
                .where(todoList.member.eq(member), todoList.dueDate.between(startDate, endDate))
                .orderBy(todoList.todoListId.asc())
                .fetchFirst());
    }

}