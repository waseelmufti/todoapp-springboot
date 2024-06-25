package com.todoapp.commands;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import com.todoapp.payloads.UserDTO;
import com.todoapp.services.UserService;

@ShellComponent
public class CreateDummyUserCommand {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @ShellMethod(value = "Create Dummy User", key = "create-dummyUser")
    public String createUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(generateRandomName());
        userDTO.setEmail(generateRandomEmail());
        userDTO.setPassword(passwordEncoder.encode("12345678"));

        this.userService.saveUser(userDTO);
        return "Dummy User Created";
    }

    // Method to generate a random name
    private String generateRandomName() {
        String[] firstNames = { "John", "Jane", "Michael", "Emily", "David", "Sarah", "William", "Jessica", "Robert",
                "Ashley" };
        String[] lastNames = { "Smith", "Jones", "Brown", "Davis", "Miller", "Wilson", "Moore", "Taylor", "Anderson",
                "Thomas" };

        Random random = new Random();
        return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }

    // Method to generate a random email
    private String generateRandomEmail() {
        String[] domains = { "gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "icloud.com" };
        Random random = new Random();
        return generateRandomName().toLowerCase().replace(" ", ".") + "@" + domains[random.nextInt(domains.length)];
    }
}
