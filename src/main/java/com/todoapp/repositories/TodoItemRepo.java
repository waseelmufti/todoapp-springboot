package com.todoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.todoapp.entities.TodoItem;

@Repository
public interface TodoItemRepo extends JpaRepository<TodoItem, Integer> {}
