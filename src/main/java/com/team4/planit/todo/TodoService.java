package com.team4.planit.todo;

import com.team4.planit.category.Category;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.statistic.achievement.AchievementService;
import com.team4.planit.todo.dto.TodoRequestDto;
import com.team4.planit.todo.dto.TodoResponseDto;
import com.team4.planit.todoList.TodoList;
import com.team4.planit.todoList.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;
    private final Check check;
    private final AchievementService achievementService;

    @Transactional
    public TodoResponseDto createTodo(Long categoryId, TodoRequestDto requestDto, HttpServletRequest request) throws ParseException {
        Member member = check.validateMember(request);
        check.checkPastDate(requestDto.getDueDate());
        Category category = check.isPresentCategory(categoryId);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, requestDto.getDueDate())
                .orElseGet(() -> new TodoList(member, requestDto.getDueDate(), (byte) 0));
        todoListRepository.save(todoList);
        Todo todo = Todo.builder()
                .todoList(todoList)
                .member(member)
                .category(category)
                .dueDate(requestDto.getDueDate())
                .title(requestDto.getTitle())
                .memo(requestDto.getMemo())
                .isAchieved(false)
                .build();
        todoRepository.save(todo);
        achievementService.updateAchievement(member, todo.getDueDate());
        return buildTodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, HttpServletRequest request) throws ParseException {
        String dueDate = requestDto.getDueDate();
        Member member = check.validateMember(request);
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new CustomException(ErrorCode.TODO_NOT_FOUND));
        check.checkTodoAuthor(member, todo);
        if(dueDate!=null) check.checkPastDate(dueDate);
        check.checkPastDate(todo.getDueDate());
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, dueDate)
                .orElseGet(() -> new TodoList(member, dueDate, (byte) 0));
        todo.updateTodo(requestDto);
        if (dueDate != null) todo.updateTodo(todoList);
        todoListRepository.save(todoList);
        achievementService.updateAchievement(member, todo.getDueDate());
        return buildTodoResponseDto(todo);
    }

    @Transactional
    public void deleteTodo(Long todoId, HttpServletRequest request) throws ParseException {
        Member member = check.validateMember(request);
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorCode.TODO_NOT_FOUND));
        check.checkTodoAuthor(member, todo);
        String dueDate = todo.getDueDate();
        check.checkPastDate(dueDate);
        todoRepository.delete(todo);
        achievementService.updateAchievement(member, dueDate);
    }

    private TodoResponseDto buildTodoResponseDto(Todo todo) {
        return TodoResponseDto.builder()
                .todoListId(todo.getTodoList().getTodoListId())
                .todoId(todo.getTodoId())
                .title(todo.getTitle())
                .memo(todo.getMemo())
                .dueDate(todo.getDueDate())
                .isAchieved(todo.getIsAchieved())
                .build();
    }

}
