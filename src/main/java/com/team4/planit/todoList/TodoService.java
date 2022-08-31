package com.team4.planit.todoList;

import com.team4.planit.category.Category;
import com.team4.planit.category.CategoryRepository;
import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final CategoryRepository categoryRepository;
    private final TodoRepository todoRepository;
    private final Check check;

    @Transactional
    public ResponseEntity<?> createTodo(Long categoryId, TodoRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request, member);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        check.categoryCheck(category);
        Todo todo = Todo.builder()
                .member(member)
                .category(category)
                .title(requestDto.getTitle())
                .memo(requestDto.getMemo())
                .isAchieved(false)
                .build();
        todoRepository.save(todo);
        return new ResponseEntity<>(Message.success(new TodoResponseDto(todo.getTitle())), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> getAllTodos(HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request, member);
        return new ResponseEntity<>(Message.success(todoRepository.findAll()), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateTodo(Long todoId, TodoRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request, member);
        Todo todo = isPresentTodo(todoId);
        if (todo == null) {
            return new ResponseEntity<>(Message.fail("NOT_FOUND", "존재하지 않는 todo 입니다."), HttpStatus.NOT_FOUND);
        }
        todo.updateTodo(requestDto);
        return new ResponseEntity<>(Message.success(
                TodoResponseDto.builder()
                    .title(todo.getTitle())
                    .memo(todo.getMemo())
                    .isAchieved(todo.getIsAchieved())
                    .build()
        ), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> deleteTodo(Long todoId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request, member);
        Todo todo = isPresentTodo(todoId);
        if (todo == null) {
            return new ResponseEntity<>(Message.fail("NOT_FOUND", "존재하지 않는 todo 입니다."), HttpStatus.NOT_FOUND);
        }
        todoRepository.delete(todo);
        return new ResponseEntity<>(Message.success(null), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public Todo isPresentTodo(Long todoId) {
        Optional<Todo> optionalTodo = todoRepository.findById(todoId);
        return optionalTodo.orElse(null);
    }
}
