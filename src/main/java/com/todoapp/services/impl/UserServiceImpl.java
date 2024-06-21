package com.todoapp.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todoapp.entities.User;
import com.todoapp.payloads.UserDTO;
import com.todoapp.repositories.UserRepo;
import com.todoapp.services.UserService;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        User savedUser = this.userRepo.save(user);
        userDTO.setId(savedUser.getId());
        return userDTO;
    }
    
}
