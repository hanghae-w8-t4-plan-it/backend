package com.team4.planit.todo;

import com.team4.planit.global.shared.Message;
import com.team4.planit.todo.dto.TodoRequestDto;
import com.team4.planit.todo.dto.TodoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/categories/{categoryId}/todos")
    public ResponseEntity<?> createTodo(@PathVariable Long categoryId, @RequestBody TodoRequestDto requestDto,
                                        HttpServletRequest request) {
        TodoResponseDto todoResponseDto = todoService.createTodo(categoryId, requestDto, request);
        return new ResponseEntity<>(Message.success(todoResponseDto), HttpStatus.OK);
    }

    @PatchMapping("/categories/todos/{todoId}")
    public ResponseEntity<?> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto requestDto,
                                        HttpServletRequest request) throws ParseException {
        TodoResponseDto todoResponseDto = todoService.updateTodo(todoId, requestDto, request);
        return new ResponseEntity<>(Message.success(todoResponseDto), HttpStatus.OK);
    }

    @DeleteMapping("/categories/todos/{todoId}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long todoId, HttpServletRequest request) {
        todoService.deleteTodo(todoId, request);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
