package com.todoapp.services;

import java.util.List;

import com.todoapp.payloads.TodoListDTO;

public interface TodoService {
    public TodoListDTO getTodo(Integer id) throws Exception;
    public TodoListDTO getTodoByIdAndPasscode(Integer id, String passcode) throws Exception;
    public List<TodoListDTO> getAllTodos();
    public TodoListDTO addTodo(TodoListDTO todoDTo);
    public TodoListDTO updateTodo(TodoListDTO todoDTo, Integer todoId) throws Exception;
    public void deleteTodo(Integer todoId) throws Exception;
}
