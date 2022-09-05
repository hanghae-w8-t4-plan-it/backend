package com.team4.planit.todoList;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
}
