package com.todoapp.payloads;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class JsonResponse<T> {
    private String status;
    private Optional<T> data;
    private List<Optional<String>> errors;

    JsonResponse(String status, Optional<T> data, List<Optional<String>> errors) {
        this.status = status;
        this.data = data;
        this.errors = errors;
    }


    public static <T> JsonResponse<T> success(T data) {
        return new JsonResponse<T>("success", Optional.ofNullable(data), List.of());
    }

    public static <T> JsonResponse<T> errors(List<String> errors) {
        return new JsonResponse<T>("error", Optional.empty(), errors.stream().map(Optional::ofNullable).collect(Collectors.toList()));
    }

}
