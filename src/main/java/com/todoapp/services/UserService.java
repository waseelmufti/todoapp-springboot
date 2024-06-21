package com.todoapp.services;

import com.todoapp.payloads.UserDTO;

public interface UserService {
    public UserDTO saveUser(UserDTO userDTO);
}
