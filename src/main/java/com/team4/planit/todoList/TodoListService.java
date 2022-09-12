package com.team4.planit.todoList;

import com.team4.planit.category.CategoryService;
import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import com.team4.planit.todo.TodoRepository;
import com.team4.planit.todoList.dto.TodoListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<?> createTodoList(String dueDate, String planet, HttpServletRequest request) {
        Member member = check.validateMember(request);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, dueDate).orElse(null);
        if(todoList==null) {
            todoListRepository.save(new TodoList(member, dueDate, planet));
            return categoryService.getAllCategories(dueDate, request);
        }
        todoList.update(planet);
        todoListRepository.save(todoList);
        return categoryService.getAllCategories(dueDate, request);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getTodoListByYearAndMonth(String year, String month, HttpServletRequest request) {
        check.validateMember(request);
        List<TodoList> todoLists = todoListRepositorySupport.findAllByYearAndMonth(year, month);
        List<TodoListResponseDto> todoListResponseDtoList = new ArrayList<>();
        for (TodoList todoList : todoLists) {
            todoListResponseDtoList.add(
                    new TodoListResponseDto(todoList.getDueDate(),
                            todoRepository.countAllByTodoListAndIsAchieved(todoList, false))
            );
        }
        return new ResponseEntity<>(Message.success(todoListResponseDtoList), HttpStatus.OK);
    }

}
