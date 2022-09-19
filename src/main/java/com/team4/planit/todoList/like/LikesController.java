package com.team4.planit.todoList.like;

import com.team4.planit.global.shared.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("/todo-list/{todoListId}/likes")
    public ResponseEntity<?> todoListLike(@PathVariable Long todoListId, HttpServletRequest request) {
        Boolean isLikeSuccess = likesService.todoListLike(todoListId, request);
        return new ResponseEntity<>(Message.success(isLikeSuccess), HttpStatus.OK);
    }
}
