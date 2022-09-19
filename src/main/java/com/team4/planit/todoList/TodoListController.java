package com.team4.planit.todoList;

import com.team4.planit.category.dto.CategoryDetailResponseDto;
import com.team4.planit.global.shared.Message;
import com.team4.planit.todoList.dto.TodoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;

    @GetMapping("/todo-list/{year}/{month}")
    public ResponseEntity<?> getTodoList(@PathVariable String year, @PathVariable String month,
                                         HttpServletRequest request) {
        List<TodoListResponseDto> todoListResponseDtoList = todoListService.getTodoListByYearAndMonth(year, month, request);
        return new ResponseEntity<>(Message.success(todoListResponseDtoList), HttpStatus.OK);
    }

    @PostMapping("/todo-list/today")
    public ResponseEntity<?> createTodoList(@RequestParam String dueDate,
                                            @RequestParam(required = false) String planet,
                                            HttpServletRequest request) {
        List<CategoryDetailResponseDto> categoryDetailResponseDtoList = todoListService.createTodoList(dueDate, planet, request);
        return new ResponseEntity<>(Message.success(categoryDetailResponseDtoList), HttpStatus.OK);
    }

}
