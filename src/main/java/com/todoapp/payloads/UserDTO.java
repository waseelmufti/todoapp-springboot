package com.todoapp.payloads;

import java.util.List;

import com.todoapp.entities.TodoList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Boolean active = true;
    private String role = "ROLE_USER";
    private List<TodoList> todoLists;
}
