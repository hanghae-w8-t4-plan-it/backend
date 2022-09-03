package com.team4.planit.todoList;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodoListController {
    private final TodoListService todoListService;

}
