package com.team4.planit.todoList.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.team4.planit.todoList.like.QLikes.likes;
import static com.team4.planit.todoList.QTodoList.todoList;

@Repository
public class LikesRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public LikesRepositorySupport(JPAQueryFactory queryFactory) {
        super(Likes.class);
        this.queryFactory = queryFactory;
    }

    public Integer findMonthlyTotalLikes(Member member, String month) {
        return Math.toIntExact(queryFactory
                .select(likes.count())
                .from(likes)
                .innerJoin(todoList)
                .on(likes.todoList.eq(todoList))
                .where(todoList.member.eq(member), todoList.dueDate.contains(month))
                .fetchFirst());
    }
}