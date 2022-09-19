package com.team4.planit.todoList;

import com.team4.planit.category.CategoryService;
import com.team4.planit.category.dto.CategoryDetailResponseDto;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.todoList.dto.TodoListRequestDto;
import com.team4.planit.todoList.dto.TodoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final Check check;
    private final TodoListRepositorySupport todoListRepositorySupport;
    private final TodoListRepository todoListRepository;
    private final CategoryService categoryService;

    @Transactional
    public List<CategoryDetailResponseDto> createTodoList(String dueDate, Byte planetType, HttpServletRequest request) {
        Member member = check.validateMember(request);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, dueDate)
                .orElseGet(()-> new TodoList(member, dueDate));
        todoList.update(planetType);
        todoListRepository.save(todoList);
        return categoryService.getAllCategories(dueDate, null, request);
    }

    @Transactional(readOnly = true)
    public List<String> getUnAchievedDueDatesByYearAndMonth(String year, String month, HttpServletRequest request) {
        Member member = check.validateMember(request);
        return todoListRepositorySupport.findUnAchievedDueDatesByMemberAndYearAndMonth(member, year, month);
    }

    @Transactional
    public TodoListResponseDto updatePlanet(TodoListRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, requestDto.getDueDate()).orElseThrow(
                () -> new CustomException(ErrorCode.TODO_LIST_NOT_FOUND)
        );
        todoList.update(requestDto.getPlanetSize(), requestDto.getPlanetColor());
        return TodoListResponseDto.builder()
                .dueDate(todoList.getDueDate())
                .planetType(todoList.getPlanetType())
                .planetSize(todoList.getPlanetSize())
                .planetColor(todoList.getPlanetColor())
                .planetLevel(todoList.getPlanetLevel())
                .build();
    }
}
