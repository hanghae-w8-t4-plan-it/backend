package com.team4.planit.todoList;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TodoListRequestDto {
    String dueDate;
    String planet;
    public TodoListRequestDto(String dueDate,String planet){
        this.dueDate=dueDate;
        this.planet=planet;
    }
}
