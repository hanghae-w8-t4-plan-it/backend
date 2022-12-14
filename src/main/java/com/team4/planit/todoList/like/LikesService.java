package com.team4.planit.todoList.like;

import com.team4.planit.global.shared.Check;
import com.team4.planit.member.Member;
import com.team4.planit.member.dto.MemberResponseDto;
import com.team4.planit.todoList.TodoList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final Check check;
    private final LikesRepository likesRepository;
    private final LikesRepositorySupport likesRepositorySupport;


    @Transactional
    public Boolean todoListLike(Long todoListId, HttpServletRequest request) {
        Member member = check.validateMember(request);
        TodoList todoList = check.isPresentTodoList(todoListId);
        Optional<Likes> findLike = likesRepository.findByMemberAndTodoList(member, todoList);
        if (findLike.isEmpty()) {
            likesRepository.save(new Likes(member, todoList));
            return true;
        }
        likesRepository.deleteById(findLike.get().getLikesId());
        return false;
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getTodoListLike(Long todoListId, HttpServletRequest request) {
        check.validateMember(request);
        TodoList todoList = check.isPresentTodoList(todoListId);
        return likesRepositorySupport.findMemberByTodoList(todoList);
    }
}
