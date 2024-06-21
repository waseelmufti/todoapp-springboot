package com.todoapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.todoapp.payloads.UserDTO;
import com.todoapp.services.UserService;

@SpringBootApplication
// @EnableAutoConfiguration(exclude = { 
// 	org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
// })
public class TodoappApplication implements CommandLineRunner{
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;
	@Override
	public void run(String... args) throws Exception {
		// UserDTO userDTO = new UserDTO();
		// userDTO.setName("user");
		// userDTO.setEmail("user@info.com");
		// userDTO.setPassword(passwordEncoder.encode("12345678"));

		// this.userService.saveUser(userDTO);
	}

	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}

}
