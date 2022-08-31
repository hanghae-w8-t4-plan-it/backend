package com.team4.planit.todoList;

import com.team4.planit.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    Integer countAllByCategory(Category category);
}
