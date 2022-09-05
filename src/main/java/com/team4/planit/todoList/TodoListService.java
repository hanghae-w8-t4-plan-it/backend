package com.team4.planit.todoList;

import com.team4.planit.category.CategoryService;
import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
@RequiredArgsConstructor
public class TodoListService {
    private final Check check;
    private final TodoListRepository todoListRepository;
    private final CategoryService categoryService;

    public ResponseEntity<?> getPlanet(TodoListRequestDto requestDto, HttpServletRequest request) {
        Member member = check.validateMember(request);
        TodoList todoList=todoListRepository.findByMemberAndDueDate(member, requestDto.getDueDate())
                .orElse(todoListRepository.save(new TodoList(member, requestDto.getDueDate(), requestDto.getPlanet())));
        todoList.update(requestDto.getPlanet());
        return categoryService.getAllCategories(requestDto.getDueDate(), request);
    }
}
