package com.team4.planit.todoList;

import com.team4.planit.global.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping()
    public ResponseEntity<?> createTodo(@RequestBody TodoRequestDto requestDto, HttpServletRequest request) {
        return todoService.createTodo(requestDto, request);
    }

    @GetMapping()
    public ResponseEntity<?> getAllTodos(HttpServletRequest request) {
        return todoService.getAllTodos(request);
    }

    @PatchMapping("/{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto requestDto, HttpServletRequest request) {
        return todoService.updateTodo(todoId, requestDto, request);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoId, HttpServletRequest request) {
        return todoService.deleteTodo(todoId, request);
    }

}
