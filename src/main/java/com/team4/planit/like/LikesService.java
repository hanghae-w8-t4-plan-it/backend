package com.team4.planit.like;

import com.team4.planit.global.shared.Check;
import com.team4.planit.global.shared.Message;
import com.team4.planit.member.Member;
import com.team4.planit.todoList.TodoList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final Check check;

    @Transactional
    public ResponseEntity<?> todoListLike(Long todoListId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        TodoList todoList = check.isPresentTodoList(todoListId);
        Optional<Likes> findLike = likesRepository.findByMemberAndAndTodoList(member, todoList);
        if (findLike.isEmpty()) {
            likesRepository.save(new Likes(member, todoList));
            return new ResponseEntity<>(Message.success(true), HttpStatus.OK);
        }
        likesRepository.deleteById(findLike.get().getLikesId());
        return new ResponseEntity<>(Message.success(false), HttpStatus.OK);
    }
}
