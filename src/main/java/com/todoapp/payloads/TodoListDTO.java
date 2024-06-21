package com.todoapp.payloads;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TodoListDTO {
    private Integer id;
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;
    private String slug;
    private String description;
    private String viewPasscode;
    private UserDTO user;
    @NotBlank(message = "Is Public is required")
    private String isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
