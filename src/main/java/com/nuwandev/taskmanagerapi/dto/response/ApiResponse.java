package com.nuwandev.taskmanagerapi.dto.response;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data) {
}