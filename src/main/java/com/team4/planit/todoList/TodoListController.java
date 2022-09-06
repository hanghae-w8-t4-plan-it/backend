package com.team4.planit.todoList;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;

    @GetMapping("/todo-list/{year}/{month}")
    public ResponseEntity<?> getTodoList(@PathVariable String year, @PathVariable String month,
                                         HttpServletRequest request) {
        return todoListService.getTodoListByYearAndMonth(year, month, request);
    }

    @PostMapping("/todo-list/today")
    public ResponseEntity<?> createTodoList(@RequestParam String dueDate,
                                            @RequestParam(required = false) String planet,
                                            HttpServletRequest request) {
        return todoListService.createTodoList(dueDate, planet, request);
    }

}
