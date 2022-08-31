package com.team4.planit.todoList;

import com.team4.planit.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {


}
