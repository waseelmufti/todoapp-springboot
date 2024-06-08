package com.todoapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todoapp.entities.TodoItem;

@Repository
public interface TodoItemRepo extends JpaRepository<TodoItem, Integer> {
    List<TodoItem> findByTodoList_Id(Integer todoId);
    List<TodoItem> findByTodoList_IdAndIsCompletedTrue(Integer todoId);
    @Modifying
    @Query("UPDATE TodoItem t SET t.isCompleted = :isCompleted WHERE t.id = :todoItemId AND t.todoList.id = :todoId")
    Integer updateStatusById(@Param("todoId") Integer todoId, @Param("todoItemId") Integer todoItemId, @Param("isCompleted") Boolean isCompleted);
    
}
