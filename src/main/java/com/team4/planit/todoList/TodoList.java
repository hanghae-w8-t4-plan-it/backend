package com.team4.planit.todoList;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "todo_list_due_date")
    private String dueDate;

    @Column(name = "todo_list_planet_type")
    private Byte planetType;

    @Column(name = "todo_list_planet_size")
    private Short planetSize;

    @Column(name = "todo_list_planet_color")
    private Byte planetColor;

    @Column(name = "todo_list_planet_level")
    private Byte planetLevel;

    public TodoList(Member member, String dueDate, Byte planetType) {
        this.member = member;
        this.dueDate = dueDate;
        this.planetType = planetType;
    }
    public TodoList(Member member, String dueDate, Byte planetType, Short planetSize, Byte planetColor) {
        this.member = member;
        this.dueDate = dueDate;
        this.planetType = planetType;
        this.planetSize = planetSize;
        this.planetColor = planetColor;
    }

    public void update(Byte planetType) {
        this.planetType = planetType;
        this.planetSize = 44;
        this.planetLevel = 1;
    }

    public void update(Short planetSize, Byte planetColor) {
        this.planetSize = planetSize;
        this.planetColor = planetColor;
    }
    public void updatePlanet(Byte level, Short planetSize){
        this.planetLevel = level;
        this.planetSize = planetSize;
    }
}
