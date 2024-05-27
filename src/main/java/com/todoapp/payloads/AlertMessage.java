package com.todoapp.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlertMessage {
    private String type;
    private String message;
}
