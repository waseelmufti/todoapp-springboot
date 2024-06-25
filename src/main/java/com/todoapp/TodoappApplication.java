package com.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @EnableAutoConfiguration(exclude = { 
// 	org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
// })
public class TodoappApplication{
	public static void main(String[] args) {
		SpringApplication.run(TodoappApplication.class, args);
	}

}
