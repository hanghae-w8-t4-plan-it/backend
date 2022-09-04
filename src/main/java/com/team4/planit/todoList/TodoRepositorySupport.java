package com.team4.planit.todoList;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

    public List<CategoryResponseDto> findAllCategoriesAndTodos() {
        return queryFactory
                .select(Projections.fields(
                        CategoryResponseDto.class,
                        category.categoryId,
                        category.categoryName,
                        category.categoryColor,
                        category.isPublic,
                        category.categoryStatus,
                        todo
                ))
                .from(todo)
                .leftJoin(category)
                .on(category.categoryId.eq(todo.category.categoryId))
                .where(category.categoryId.eq(todo.category.categoryId))
                .groupBy(category)
                .fetch();
    }

}