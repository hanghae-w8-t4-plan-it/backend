package com.team4.planit.todoList;

import com.team4.planit.category.dto.CategoryDetailResponseDto;
import com.team4.planit.global.shared.Message;
import com.team4.planit.todoList.dto.TodoListRequestDto;
import com.team4.planit.todoList.dto.TodoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo-list")
public class TodoListController {
    private final TodoListService todoListService;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> getUnAchievedDueDates(@PathVariable String year, @PathVariable String month,
                                         HttpServletRequest request) {
        List<String> todoListResponseDtoList = todoListService.getUnAchievedDueDatesByYearAndMonth(year, month, request);
        return new ResponseEntity<>(Message.success(todoListResponseDtoList), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createTodoList(@RequestParam String dueDate,
                                            @RequestParam(required = false) Byte planetType,
                                            HttpServletRequest request) {
        List<CategoryDetailResponseDto> categoryDetailResponseDtoList = todoListService.createTodoList(dueDate, planetType, request);
        return new ResponseEntity<>(Message.success(categoryDetailResponseDtoList), HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<?> updatePlanet(@RequestBody TodoListRequestDto requestDto, HttpServletRequest request) {
        TodoListResponseDto todoListResponseDto = todoListService.updatePlanet(requestDto, request);
        return new ResponseEntity<>(Message.success(todoListResponseDto), HttpStatus.OK);
    }
}
