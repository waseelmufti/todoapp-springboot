package com.todoapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.todoapp.entities.TodoList;

@Repository
public interface TodoListRepo extends JpaRepository<TodoList, Integer> {
    List<TodoList> findByUserIdOrderByIdDesc(Integer userId);
    Optional<TodoList> findByIdAndUserId(Integer todoId,Integer userId);
    Optional<TodoList> findByIdAndViewPasscodeAndIsPublicTrue(Integer todoId, String passcode);
}
