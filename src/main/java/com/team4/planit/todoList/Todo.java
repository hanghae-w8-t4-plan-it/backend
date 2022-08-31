package com.team4.planit.todoList;

import com.team4.planit.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column
    private String title;

    @Column
    private String memo;

    public Todo(Member member, String title) {
        this.member = member;
        this.title = title;
    }

    public Todo(Member member, String title, String memo) {
        this.member = member;
        this.title = title;
        this.memo = memo;
    }

    public void updateTodo(String title, String memo) {
        this.title = title;
        this.memo = memo;
    }
}
