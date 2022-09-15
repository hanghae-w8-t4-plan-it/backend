package com.team4.planit.category;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.member.Member;
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

    public List<String> findAllCategoryRank(Member member, String month) {
        return queryFactory
                .select(category.categoryName)
                .from(category)
                .innerJoin(todo)
                .on(category.eq(todo.category))
                .where(category.member.eq(member),
                        todo.todoList.dueDate.contains(month),
                        todo.isAchieved.eq(true))
                .groupBy(category.categoryName)
                .orderBy(category.count().desc())
                .fetch();
    }
}