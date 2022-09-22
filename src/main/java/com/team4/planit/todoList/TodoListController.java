package com.team4.planit.todoList;

import com.team4.planit.category.dto.CategoryDetailResponseDto;
import com.team4.planit.global.shared.Message;
import com.team4.planit.todoList.dto.TodoListRequestDto;
import com.team4.planit.todoList.dto.DailyTodoListResponseDto;
import com.team4.planit.todoList.dto.WeeklyTodoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todo-list")
public class TodoListController {
    private final TodoListService todoListService;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<?> getUnAchievedDueDates(@PathVariable String year, @PathVariable String month,
                                         HttpServletRequest request) {
        List<String> UnAchievedDueDates = todoListService.getUnAchievedDueDatesByYearAndMonth(year, month, request);
        return new ResponseEntity<>(Message.success(UnAchievedDueDates), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> updatePlanetType(@RequestParam String dueDate,
                                            @RequestParam Byte planetType,
                                            HttpServletRequest request) {
        List<CategoryDetailResponseDto> categoryDetailResponseDtoList = todoListService.updatePlanetType(dueDate, planetType, request);
        return new ResponseEntity<>(Message.success(categoryDetailResponseDtoList), HttpStatus.OK);
    }

    @PatchMapping()
    public ResponseEntity<?> updatePlanet(@RequestBody TodoListRequestDto requestDto, HttpServletRequest request) {
        DailyTodoListResponseDto dailyTodoListResponseDto = todoListService.updatePlanet(requestDto, request);
        return new ResponseEntity<>(Message.success(dailyTodoListResponseDto), HttpStatus.OK);
    }

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyTodoList(@RequestParam(required = false) Long memberId,
                                         @RequestParam String dueDate,
                                         HttpServletRequest request) {
        DailyTodoListResponseDto dailyTodoListResponseDto = todoListService.getDailyTodoList(memberId, dueDate, request);
        return new ResponseEntity<>(Message.success(dailyTodoListResponseDto), HttpStatus.OK);
    }

    @GetMapping("/weekly")
    public ResponseEntity<?> getWeeklyTodoList(@RequestParam(required = false) Long memberId,
                                           @RequestParam String startDate,
                                           HttpServletRequest request) throws ParseException {
        WeeklyTodoListResponseDto weeklyTodoListResponseDto = todoListService.getWeeklyTodoList(memberId, startDate, request);
        return new ResponseEntity<>(Message.success(weeklyTodoListResponseDto), HttpStatus.OK);
    }
}
