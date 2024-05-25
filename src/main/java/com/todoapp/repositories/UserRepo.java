package com.todoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoapp.entities.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{}
