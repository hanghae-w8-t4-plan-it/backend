package com.team4.planit.todoList;

import com.team4.planit.member.Member;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//@IdClass(TodoListMultiID.class)
@Getter
@NoArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_list_id")
    private Long todoListId;

//    @Id
    @Column(name = "due_date")
    private String dueDate;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public TodoList(Member member, String dueDate) {
        this.member = member;
        this.dueDate = dueDate;
    }
}
