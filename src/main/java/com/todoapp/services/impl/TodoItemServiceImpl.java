package com.todoapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.entities.TodoItem;
import com.todoapp.entities.TodoList;
import com.todoapp.payloads.TodoItemDTO;
import com.todoapp.repositories.TodoItemRepo;
import com.todoapp.repositories.TodoListRepo;
import com.todoapp.services.TodoItemService;

import jakarta.transaction.Transactional;

@Service
public class TodoItemServiceImpl implements TodoItemService{
    @Autowired
    TodoListRepo todoRepo;
    @Autowired
    TodoItemRepo todoItemRepo;

    @Override
    public TodoItemDTO saveTodoItem(Integer todoId, TodoItemDTO todoItemDTO) throws Exception {
       TodoList todoList =this.todoRepo.findById(todoId).orElseThrow(() -> new Exception("Todo not found"));
       TodoItem todoItem = new TodoItem();
       todoItem.setTitle(todoItemDTO.getTitle());
       todoItem.setDescription(todoItemDTO.getDescription());
       todoItem.setCompleted(todoItemDTO.getIsCompleted() == "yes" ? true : false);
       todoItem.setTodoList(todoList);
       for (TodoItem item : todoList.getTodoItems()) {
        System.out.println(item.getTitle());
        
       }
       todoList.getTodoItems().add(todoItem);
       this.todoRepo.save(todoList);

        return todoItemDTO;
    }

    @Override
    public List<TodoItemDTO> getAllTodoItem(Integer todoId) throws Exception{
        TodoList todoList = this.todoRepo.findById(todoId).orElseThrow(() -> new Exception("Todo not found"));

        List<TodoItem> todoItems = this.todoItemRepo.findByTodoList_Id(todoList.getId());
        List<TodoItemDTO> todoItemDTOs = todoItems.stream().map((item) -> {
            return new TodoItemDTO(item.getId(), item.getTitle(), item.getDescription(), item.isCompleted() ? "yes" : "no", item.getCreatedAt(), item.getUpdatedAt());
        }).collect(Collectors.toList());
        return todoItemDTOs;
    }
        
        
    @Override
    public List<TodoItemDTO> getAllCompletedTodoItem(Integer todoId) throws Exception {
        TodoList todoList = this.todoRepo.findById(todoId).orElseThrow(() -> new Exception("Todo not found"));

        List<TodoItem> todoItems = this.todoItemRepo.findByTodoList_IdAndIsCompletedTrue(todoList.getId());
        List<TodoItemDTO> todoItemDTOs = todoItems.stream().map((item) -> {
            return new TodoItemDTO(item.getId(), item.getTitle(), item.getDescription(), item.isCompleted() ? "yes" : "no", item.getCreatedAt(), item.getUpdatedAt());
        }).collect(Collectors.toList());
        return todoItemDTOs;
    }

    @Transactional
    @Override
    public Integer changeStatus(Integer todoId, Integer todoItemId, Boolean status) throws Exception {
        try {
            return this.todoItemRepo.updateStatusById(todoId, todoItemId, status);
        } catch (Exception e) {
            throw e;
        }
    }

    

}
