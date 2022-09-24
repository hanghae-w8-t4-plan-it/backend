package com.team4.planit.todoList.like;

import com.team4.planit.member.Member;
import com.team4.planit.todoList.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByMemberAndTodoList(Member member, TodoList todoList);

//    List<String> findTopLikeMembers(@Param("memberId") Long memberId, @Param("month") String month);
}
