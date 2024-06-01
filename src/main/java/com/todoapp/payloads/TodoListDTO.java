package com.todoapp.payloads;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoListDTO {
    private Integer id;
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;
    private String slug;
    private String description;
    private String viewPasscode;
    @NotBlank(message = "Is Public is required")
    private String isPublic;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
