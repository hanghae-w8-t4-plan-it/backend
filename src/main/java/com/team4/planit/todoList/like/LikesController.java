package com.team4.planit.todoList.like;

import com.team4.planit.global.shared.Message;
import com.team4.planit.member.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping("/todo-list/{todoListId}/likes")
@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping()
    public ResponseEntity<?> todoListLike(@PathVariable Long todoListId, HttpServletRequest request) {
        Boolean isLikeSuccess = likesService.todoListLike(todoListId, request);
        return new ResponseEntity<>(Message.success(isLikeSuccess), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getTodoListLike(@PathVariable Long todoListId, HttpServletRequest request) {
        List<MemberResponseDto> memberResponseDto = likesService.getTodoListLike(todoListId, request);
        return new ResponseEntity<>(Message.success(memberResponseDto), HttpStatus.OK);
    }
}
