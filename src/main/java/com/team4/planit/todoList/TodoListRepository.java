package com.team4.planit.todoList;

import com.team4.planit.category.Category;
import com.team4.planit.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    Optional<TodoList> findByMemberAndDueDate(Member member, String dueDate);
}
