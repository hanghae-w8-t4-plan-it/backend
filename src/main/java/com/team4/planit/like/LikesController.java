package com.team4.planit.like;

import lombok.RequiredArgsConstructor;
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
        return likesService.todoListLike(todoListId, request);
    }
}
