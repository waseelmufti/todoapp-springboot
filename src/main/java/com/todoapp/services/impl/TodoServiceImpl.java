package com.todoapp.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.entities.TodoList;
import com.todoapp.entities.User;
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
        User authUser = Utils.getAuthUser();
        List<TodoList> todos = this.todoListRepo.findByUserIdOrderByIdDesc(authUser.getId());
        List<TodoListDTO> todoListDTO = todos.stream().map((todo) -> {
            TodoListDTO todoDTo = new TodoListDTO();
            todoDTo.setId(todo.getId());
            todoDTo.setTitle(todo.getTitle());
            todoDTo.setSlug(todo.getSlug());
            todoDTo.setDescription(todo.getDescription());
            todoDTo.setViewPasscode(todo.getViewPasscode());
            todoDTo.setIsPublic(todo.isPublic() ? "yes" : "no");
            todoDTo.setCreatedAt(todo.getCreatedAt());
            todoDTo.setUpdatedAt(todo.getUpdatedAt());
            return todoDTo;
        })
        .collect(Collectors.toList());
        return todoListDTO;
    }



    @Override
    public TodoListDTO getTodoByIdAndPasscode(Integer id, String passcode) throws Exception {
        TodoList todo = this.todoListRepo.findByIdAndViewPasscodeAndIsPublicTrue(id, passcode).orElseThrow(() -> new Exception("Todo not found Or you don't have permission"));

        TodoListDTO todoDTo = new TodoListDTO();
        todoDTo.setId(todo.getId());
        todoDTo.setTitle(todo.getTitle());
        todoDTo.setSlug(todo.getSlug());
        todoDTo.setDescription(todo.getDescription());
        todoDTo.setViewPasscode(todo.getViewPasscode());
        todoDTo.setIsPublic(todo.isPublic() ? "yes" : "no");
        todoDTo.setCreatedAt(todo.getCreatedAt());
        todoDTo.setUpdatedAt(todo.getUpdatedAt());
        return todoDTo;
    }



    @Override
    public TodoListDTO getTodo(Integer id) throws Exception{
        User authUser = Utils.getAuthUser();
        TodoList todo = this.todoListRepo.findByIdAndUserId(id, authUser.getId()).orElseThrow(() -> new Exception("Todo not found"));

        TodoListDTO todoDTo = new TodoListDTO();
        todoDTo.setId(todo.getId());
        todoDTo.setTitle(todo.getTitle());
        todoDTo.setSlug(todo.getSlug());
        todoDTo.setDescription(todo.getDescription());
        todoDTo.setViewPasscode(todo.getViewPasscode());
        todoDTo.setIsPublic(todo.isPublic() ? "yes" : "no");
        todoDTo.setCreatedAt(todo.getCreatedAt());
        todoDTo.setUpdatedAt(todo.getUpdatedAt());
        return todoDTo;
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
        todo.setUser(Utils.getAuthUser());

        this.todoListRepo.save(todo);
        return todoDTo;
    }

    

    @Override
    public TodoListDTO updateTodo(TodoListDTO todoDTo, Integer todoId) throws Exception {
        User authUser = Utils.getAuthUser();
        TodoList todoList = this.todoListRepo.findByIdAndUserId(todoId, authUser.getId()).orElseThrow(() -> new Exception("Todo Not Found"));

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
        
        todoDTo = new TodoListDTO();
        todoDTo.setId(updateTodoList.getId());
        todoDTo.setTitle(updateTodoList.getTitle());
        todoDTo.setSlug(updateTodoList.getSlug());
        todoDTo.setDescription(updateTodoList.getDescription());
        todoDTo.setViewPasscode(updateTodoList.getViewPasscode());
        todoDTo.setIsPublic(updateTodoList.isPublic() ? "yes" : "no");
        todoDTo.setCreatedAt(updateTodoList.getCreatedAt());
        todoDTo.setUpdatedAt(updateTodoList.getUpdatedAt());
        return todoDTo;
    }



    @Override
    public void deleteTodo(Integer todoId) throws Exception{
        User authUser = Utils.getAuthUser();
        TodoList todoList =  this.todoListRepo.findByIdAndUserId(todoId, authUser.getId()).orElseThrow(() -> new Exception("Todo not found"));
        this.todoListRepo.delete(todoList);
    }

    
}
