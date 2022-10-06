package com.team4.planit.todo;

import com.team4.planit.category.Category;
import com.team4.planit.member.Member;
import com.team4.planit.todo.dto.TodoRequestDto;
import com.team4.planit.todoList.TodoList;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Table(indexes = @Index(name = "t_category_and_due_date", columnList = "category_id, todo_due_date"))
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
    public Todo(TodoList todoList, String dueDate, String title, String memo, Boolean isAchieved) {
        this.todoList = todoList;
        this.dueDate = dueDate;
        this.title = title;
        this.memo = memo;
        this.isAchieved = isAchieved;
    }

    public void updateTodo(TodoRequestDto requestDto) {
        if (requestDto.getTitle() != null) this.title = requestDto.getTitle();
        if (requestDto.getMemo() != null) this.memo = requestDto.getMemo();
        if (requestDto.getIsAchieved() != null) this.isAchieved = requestDto.getIsAchieved();
        if (requestDto.getDueDate() != null) this.dueDate = requestDto.getDueDate();
    }
    public void updateTodo(TodoList todoList){
        this.todoList=todoList;
    }
}
