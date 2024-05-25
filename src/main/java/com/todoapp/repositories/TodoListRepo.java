package com.todoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.todoapp.entities.TodoList;

@Repository
public interface TodoListRepo extends JpaRepository<TodoList, Integer> {}
