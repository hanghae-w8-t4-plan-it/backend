package com.team4.planit.todoList;

import com.team4.planit.category.Category;
import com.team4.planit.member.Member;
import lombok.*;

import javax.persistence.*;

@Entity
//@IdClass(TodoMultiID.class)
@Getter
@NoArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long todoId;

//    @Id
//    @ManyToOne
//    @JoinColumns(value = {
//            @JoinColumn(name = "todo_list_id", referencedColumnName = "todo_list_id"),
//            @JoinColumn(name = "due_date", referencedColumnName = "due_date")
//    })
//    private TodoList todoList;

    @ManyToOne
    @JoinColumn(name = "todo_list_id")
    private TodoList todoList;

    @Column(name = "due_date")
    private String dueDate;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "category_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column
    private String memo;

    @Column(nullable = false)
    private Boolean isAchieved;

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

    public void updateTodo(TodoRequestDto requestDto) {
        if (requestDto.getTitle() != null) this.title = requestDto.getTitle();
        if (requestDto.getMemo() != null) this.memo = requestDto.getMemo();
        if (requestDto.getIsAchieved() != this.isAchieved) this.isAchieved = requestDto.getIsAchieved();
    }
}
