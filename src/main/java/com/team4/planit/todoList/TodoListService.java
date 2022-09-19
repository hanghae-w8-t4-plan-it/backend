package com.team4.planit.todoList;

import com.team4.planit.category.CategoryService;
import com.team4.planit.category.dto.CategoryDetailResponseDto;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.todo.TodoRepository;
import com.team4.planit.todoList.dto.TodoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoListService {
    private final Check check;
    private final TodoListRepositorySupport todoListRepositorySupport;
    private final TodoListRepository todoListRepository;
    private final CategoryService categoryService;
    private final TodoRepository todoRepository;

    public List<CategoryDetailResponseDto> createTodoList(String dueDate, String planet, HttpServletRequest request) {
        Member member = check.validateMember(request);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, dueDate)
                .orElseGet(()-> new TodoList(member, dueDate));
        todoList.update(planet);
        todoListRepository.save(todoList);
        return categoryService.getAllCategories(dueDate, null, request);
    }

    @Transactional(readOnly = true)
    public List<TodoListResponseDto> getTodoListByYearAndMonth(String year, String month, HttpServletRequest request) {
        check.validateMember(request);
        List<TodoList> todoLists = todoListRepositorySupport.findAllByYearAndMonth(year, month);
        List<TodoListResponseDto> todoListResponseDtoList = new ArrayList<>();
        for (TodoList todoList : todoLists) {
            todoListResponseDtoList.add(
                    new TodoListResponseDto(todoList.getDueDate(),
                            todoRepository.countAllByTodoListAndIsAchieved(todoList, false))
            );
        }
        return todoListResponseDtoList;
    }

}
