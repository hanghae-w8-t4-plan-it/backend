package com.team4.planit.todoList;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.category.Category;
import com.team4.planit.category.CategoryResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team4.planit.category.QCategory.category;
import static com.team4.planit.todoList.QTodo.todo;

@Repository
public class TodoRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public TodoRepositorySupport(JPAQueryFactory queryFactory) {
        super(Todo.class);
        this.queryFactory = queryFactory;
    }

    public List<TodoResponseDto> findAllTodos(Category category) {
        return queryFactory
                .select(Projections.fields(
                        TodoResponseDto.class,
                        todo.todoList.todoListId,
                        todo.dueDate,
                        todo.title,
                        todo.memo,
                        todo.isAchieved
                ))
                .from(todo)
                .where(todo.category.eq(category))
                .fetch();
    }

}