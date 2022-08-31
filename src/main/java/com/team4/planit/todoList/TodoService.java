package com.team4.planit.todoList;

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

    private final TodoRepository todoRepository;
    private final Check check;

    @Transactional
    public ResponseEntity<?> createTodo(TodoRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        check.accessTokenCheck(request, member);
        Todo todo = new Todo(member, requestDto.getTitle());
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
        todo.updateTodo(requestDto.getTitle(), requestDto.getMemo());
        return new ResponseEntity<>(Message.success(new TodoResponseDto(todo.getTitle(), todo.getMemo())), HttpStatus.OK);
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
