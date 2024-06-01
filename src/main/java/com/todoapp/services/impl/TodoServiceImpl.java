package com.todoapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.entities.TodoList;
import com.todoapp.payloads.TodoListDTO;
import com.todoapp.repositories.TodoListRepo;
import com.todoapp.services.TodoService;
import com.todoapp.utils.Utils;

@Service
public class TodoServiceImpl implements TodoService{
    @Autowired
    private TodoListRepo todoListRepo;


    @Override
    public List<TodoListDTO> getAllTodos() {
        List<TodoList> todos = this.todoListRepo.findAll();
        List<TodoListDTO> todoListDTO = todos.stream().map((todo) -> new TodoListDTO(todo.getId(), todo.getTitle(), todo.getSlug(), 
                todo.getDescription(), todo.getViewPasscode(), todo.isPublic() ? "yes" : "no", 
                todo.getCreatedAt(), todo.getUpdatedAt())
            )
            .collect(Collectors.toList());
        return todoListDTO;
    }



    @Override
    public TodoListDTO getTodo(Integer id) throws Exception{
        TodoList todo = this.todoListRepo.findById(id).orElseThrow(() -> new Exception("Todo not found"));

        return new TodoListDTO(todo.getId(), todo.getTitle(), todo.getSlug(),  todo.getDescription(), todo.getViewPasscode(),
                    todo.isPublic() ? "yes" : "no", todo.getCreatedAt(), todo.getUpdatedAt());
    }


    @Override
    public TodoListDTO addTodo(TodoListDTO todoDTo) {
        todoDTo.setSlug(Utils.toSlug(todoDTo.getTitle()));
        if (todoDTo.getIsPublic().equals("yes")) {
            todoDTo.setViewPasscode(Utils.generateRandomString(10));
        }

        // Now create todo
        TodoList todo = new TodoList();
        todo.setTitle(todoDTo.getTitle());
        todo.setDescription(todoDTo.getDescription());
        todo.setSlug(todoDTo.getSlug());
        todo.setPublic(todoDTo.getIsPublic().equals("yes") ? true : false);
        todo.setViewPasscode(todoDTo.getViewPasscode());

        this.todoListRepo.save(todo);
        return todoDTo;
    }

    

    @Override
    public TodoListDTO updateTodo(TodoListDTO todoDTo, Integer todoId) throws Exception {
        TodoList todoList = this.todoListRepo.findById(todoId).orElseThrow(() -> new Exception("Todo Not Found"));

        todoDTo.setSlug(Utils.toSlug(todoDTo.getTitle()));
        if(todoDTo.getIsPublic().equals("yes")){
            todoDTo.setViewPasscode(Utils.generateRandomString(10));
        }else{
            todoDTo.setViewPasscode(null);
        }

        todoList.setTitle(todoDTo.getTitle());
        todoList.setDescription(todoDTo.getDescription());
        todoList.setSlug(todoDTo.getSlug());
        todoList.setPublic(todoDTo.getIsPublic().equals("yes") ? true : false);
        todoList.setViewPasscode(todoDTo.getViewPasscode());

        TodoList updateTodoList = this.todoListRepo.save(todoList);
        return new TodoListDTO(todoId, updateTodoList.getTitle(), updateTodoList.getSlug(), updateTodoList.getDescription(),
                 updateTodoList.getViewPasscode(), updateTodoList.isPublic() ? "yes" : "no", 
                 updateTodoList.getCreatedAt(), updateTodoList.getUpdatedAt());
    }



    @Override
    public void deleteTodo(Integer todoId) throws Exception{
        TodoList todoList =  this.todoListRepo.findById(todoId).orElseThrow(() -> new Exception("Todo not found"));
        this.todoListRepo.delete(todoList);
    }

    
}
