package com.team4.planit.todoList;

import com.team4.planit.category.Category;
import com.team4.planit.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long todoId;

    @ManyToOne
    @JoinColumn(name = "todo_list_id")
    private TodoList todoList;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Column(name = "todo_due_date")
    private String dueDate;

    @Column(name = "todo_title", nullable = false)
    private String title;

    @Column(name = "todo_memo")
    private String memo;

    @Column(name = "todo_is_achieved", nullable = false)
    private Boolean isAchieved;

    public void updateTodo(TodoRequestDto requestDto) {
        if (requestDto.getTitle() != null) this.title = requestDto.getTitle();
        if (requestDto.getMemo() != null) this.memo = requestDto.getMemo();
        if (requestDto.getIsAchieved() != this.isAchieved) this.isAchieved = requestDto.getIsAchieved();
    }

    @Builder
    public Todo(Member member, Category category, TodoList todoList, String dueDate,
                String title, String memo, Boolean isAchieved) {
        this.member = member;
        this.category = category;
        this.todoList = todoList;
        this.dueDate = dueDate;
        this.title = title;
        this.memo = memo;
        this.isAchieved = isAchieved;
    }

    @Builder
    public Todo(TodoList todoList, String dueDate,
                String title, String memo, Boolean isAchieved) {
        this.todoList = todoList;
        this.dueDate = dueDate;
        this.title = title;
        this.memo = memo;
        this.isAchieved = isAchieved;
    }
}
