package com.team4.planit.todoList.like;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.team4.planit.todoList.QTodoList.todoList;
import static com.team4.planit.todoList.like.QLikes.likes;
import static com.team4.planit.member.QMember.member;

@Repository
public class LikesRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory queryFactory;

    public LikesRepositorySupport(JPAQueryFactory queryFactory) {
        super(Likes.class);
        this.queryFactory = queryFactory;
    }

    public Long findMaxLikesCountByTodoList(Member member, String month) {
        return queryFactory
                .select(likes.count())
                .from(likes)
                .innerJoin(todoList)
                .on(todoList.eq(likes.todoList))
                .where(todoList.member.eq(member), todoList.dueDate.contains(month))
                .groupBy(todoList.todoListId)
                .orderBy(likes.count().desc())
                .fetchFirst();
    }

    public List<String> findTopLikeDates(Member member, String month, Long maxLikesCount) {
        return queryFactory
                .select(todoList.dueDate)
                .from(likes)
                .innerJoin(todoList)
                .on(todoList.eq(likes.todoList))
                .where(todoList.member.eq(member), todoList.dueDate.contains(month))
                .groupBy(todoList.dueDate)
                .having(likes.count().eq(maxLikesCount))
                .fetch();
    }

    public Long findMaxLikesCountByMember(Member member1, String month) {
        return queryFactory
                .select(likes.count())
                .from(likes)
                .innerJoin(todoList)
                .on(todoList.eq(likes.todoList))
                .innerJoin(member)
                .on(member.eq(likes.member))
                .where(todoList.member.eq(member1), todoList.dueDate.contains(month))
                .groupBy(likes.member.memberId)
                .orderBy(likes.count().desc())
                .fetchFirst();
    }

    public List<String> findTopLikeMembers(Member member1, String month, Long maxLikesCount) {
        return queryFactory
                .select(member.nickname)
                .from(likes)
                .innerJoin(todoList)
                .on(todoList.eq(likes.todoList))
                .innerJoin(member)
                .on(member.eq(likes.member))
                .where(todoList.member.eq(member1), todoList.dueDate.contains(month))
                .groupBy(member.memberId)
                .having(likes.count().eq(maxLikesCount))
                .fetch();
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