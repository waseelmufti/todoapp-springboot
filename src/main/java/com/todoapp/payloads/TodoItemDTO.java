package com.todoapp.payloads;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoItemDTO {
    private int id;
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be at least 3 characters long")
    private String title;
    private String description;
    private String isCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
