package com.team4.planit.category;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.category.dto.CategoryResponseDto;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team4.planit.category.QCategory.category;
import static com.team4.planit.todo.QTodo.todo;

@Repository
public class CategoryRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public CategoryRepositorySupport(JPAQueryFactory queryFactory) {
        super(Category.class);
        this.queryFactory = queryFactory;
    }

    public List<CategoryResponseDto> findAllCategories() {
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
                .from(category)
                .leftJoin(todo)
                .on(category.categoryId.eq(todo.category.categoryId))
                .where(category.categoryId.eq(todo.category.categoryId))
                .groupBy(category)
                .fetch();
    }
}