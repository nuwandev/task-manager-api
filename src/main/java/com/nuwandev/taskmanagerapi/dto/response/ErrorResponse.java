package com.nuwandev.taskmanagerapi.dto.response;

public record ErrorResponse(
        boolean success,
        String message,
        Error[] errors) {
}

record Error(
        String field,
        String message) {
}