package com.team4.planit.todo;

import com.team4.planit.category.Category;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import com.team4.planit.statistic.achievement.AchievementService;
import com.team4.planit.todo.dto.TodoRequestDto;
import com.team4.planit.todo.dto.TodoResponseDto;
import com.team4.planit.todoList.TodoList;
import com.team4.planit.todoList.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;
    private final Check check;
    private final AchievementService achievementService;

    @Transactional
    public ResponseEntity<?> createTodo(Long categoryId, TodoRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = check.isPresentCategory(categoryId);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, requestDto.getDueDate())
                .orElseGet(() -> new TodoList(member, requestDto.getDueDate()));
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
        return new ResponseEntity<>(Message.success(buildTodoResponseDto(todo)), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getAllTodos(HttpServletRequest request) {
        check.validateMember(request);
        return new ResponseEntity<>(Message.success(todoRepository.findAll()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateTodo(Long todoId, TodoRequestDto requestDto, HttpServletRequest request) throws ParseException {
        String dueDate = requestDto.getDueDate();
        Member member = check.validateMember(request);
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()->new CustomException(ErrorCode.TODO_NOT_FOUND));
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        if(sdf.parse(todo.getDueDate()).compareTo(sdf.parse(String.valueOf(LocalDateTime.now())))<0
                    ||sdf.parse(dueDate).compareTo(sdf.parse(String.valueOf(LocalDateTime.now())))<0)
            throw new CustomException(ErrorCode.PAST_DATE);
        TodoList todoList = todoListRepository.findByMemberAndDueDate(member, requestDto.getDueDate())
                .orElseGet(() -> new TodoList(member, requestDto.getDueDate()));
        todo.updateTodo(requestDto);
        if (requestDto.getDueDate() != null) todo.updateTodo(todoList);
        todoListRepository.save(todoList);
        achievementService.updateAchievement(member, todo.getDueDate());
        return new ResponseEntity<>(Message.success(buildTodoResponseDto(todo)), HttpStatus.OK);
    }


    @Transactional
    public ResponseEntity<?> deleteTodo(Long todoId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                () -> new CustomException(ErrorCode.TODO_NOT_FOUND));
        String dueDate = todo.getDueDate();
        todoRepository.delete(todo);
        achievementService.updateAchievement(member, dueDate);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
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
