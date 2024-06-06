package com.todoapp.services;

import java.util.List;

import com.todoapp.payloads.TodoItemDTO;

public interface TodoItemService {
    public List<TodoItemDTO> getAllTodoItem(Integer todoId) throws Exception;
    public TodoItemDTO saveTodoItem(Integer todoId, TodoItemDTO todoItemDTO) throws Exception;    
}
