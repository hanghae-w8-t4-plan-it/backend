package com.team4.planit.like;

import com.team4.planit.member.Member;
import com.team4.planit.todoList.TodoList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Likes {

    @Id
    @Column(name = "likes_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "todo_list_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TodoList todoList;

    public Likes(Member member, TodoList todoList) {
        this.member = member;
        this.todoList = todoList;
    }
}
