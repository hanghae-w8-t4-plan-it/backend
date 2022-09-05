package com.team4.planit.todoList;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team4.planit.todoList.QTodo.todo;
import static com.team4.planit.todoList.QTodoList.todoList;

@Repository
public class TodoListRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public TodoListRepositorySupport(JPAQueryFactory queryFactory) {
        super(TodoList.class);
        this.queryFactory = queryFactory;
    }

    public List<TodoList> findAllByYearAndMonth(String year, String month) {
        return queryFactory
                .select(todoList)
                .from(todoList)
                .where(todoList.dueDate.substring(0, 7).eq(year + "-" + month))
                .fetch();
    }
}