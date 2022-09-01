package com.team4.planit.todoList;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TodoMultiID implements Serializable {
    private TodoList todoList;
    private Long todoId;
}
