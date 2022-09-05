package com.team4.planit.todoList;

import com.team4.planit.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_list_id")
    private Long todoListId;

    @Column(name = "todo_list_due_date")
    private String dueDate;
    @Column(name = "todo_list_planet")
    private String planet;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public TodoList(Member member, String dueDate) {
        this.member = member;
        this.dueDate = dueDate;
    }
    public TodoList(Member member, String dueDate, String planet) {
        this.member = member;
        this.dueDate = dueDate;
        this.planet = planet;
    }
    public void update(String planet){
        this.planet=planet;
    }
}
