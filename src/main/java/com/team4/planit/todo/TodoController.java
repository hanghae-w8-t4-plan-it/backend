package com.team4.planit.todo;

import com.team4.planit.todo.dto.TodoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/categories/{categoryId}/todos")
    public ResponseEntity<?> createTodo(@PathVariable Long categoryId, @RequestBody TodoRequestDto requestDto,
                                        HttpServletRequest request) {
        return todoService.createTodo(categoryId, requestDto, request);
    }

    @GetMapping("/categories/todos")
    public ResponseEntity<?> getAllTodos(HttpServletRequest request) {
        return todoService.getAllTodos(request);
    }

    @PatchMapping("/categories/todos/{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto requestDto,
                                        HttpServletRequest request) {
        return todoService.updateTodo(todoId, requestDto, request);
    }

    @DeleteMapping("/categories/todos/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoId, HttpServletRequest request) {
        return todoService.deleteTodo(todoId, request);
    }
}
