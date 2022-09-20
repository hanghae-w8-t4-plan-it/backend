package com.team4.planit.todo;

import com.team4.planit.category.Category;
import com.team4.planit.todoList.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Integer countAllByCategory(Category category);
    List<Todo> findAllByTodoList(TodoList todoList);
}
