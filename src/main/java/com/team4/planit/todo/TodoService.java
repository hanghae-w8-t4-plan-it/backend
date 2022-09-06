package com.team4.planit.todo;

import com.team4.planit.category.Category;
import com.team4.planit.category.CategoryRepository;
import com.team4.planit.global.exception.CustomException;
import com.team4.planit.global.exception.ErrorCode;
import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import com.team4.planit.todoList.TodoList;
import com.team4.planit.todoList.TodoListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;
    private final TodoListRepository todoListRepository;
    private final Check check;

    @Transactional
    public ResponseEntity<?> createTodo(Long categoryId, TodoRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Category category = check.isPresentCategory(categoryId);
        TodoList todoList = todoListRepository
                        .findByMemberAndDueDate(member, requestDto.getDueDate()).orElseThrow(
                                ()->new CustomException(ErrorCode.TODO_LIST_NOT_FOUND));
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
        return new ResponseEntity<>(Message.success(
                TodoResponseDto.builder()
                        .todoListId(todo.getTodoList().getTodoListId())
                        .todoId(todo.getTodoId())
                        .title(todo.getTitle())
                        .memo(todo.getMemo())
                        .dueDate(todo.getDueDate())
                        .isAchieved(todo.getIsAchieved())
                        .build()
        ), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getAllTodos(HttpServletRequest request) {
        Member member = check.validateMember(request);
        return new ResponseEntity<>(Message.success(todoRepository.findAll()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateTodo(Long todoId, TodoRequestDto requestDto, HttpServletRequest request) {
        check.validateMember(request);
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                ()->new CustomException(ErrorCode.TODO_NOT_FOUND));
        todo.updateTodo(requestDto);
        return new ResponseEntity<>(Message.success(
                TodoResponseDto.builder()
                        .todoListId(todo.getTodoList().getTodoListId())
                        .todoId(todo.getTodoId())
                        .title(todo.getTitle())
                        .memo(todo.getMemo())
                        .dueDate(todo.getDueDate())
                        .isAchieved(todo.getIsAchieved())
                        .build()
        ), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteTodo(Long todoId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        Todo todo = todoRepository.findById(todoId).orElseThrow(
                ()->new CustomException(ErrorCode.TODO_NOT_FOUND));
        todoRepository.delete(todo);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }
}
