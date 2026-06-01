package com.nuwandev.taskmanagerapi.dto.request;

import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;

public record UpdateTaskRequest(
        @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
        String title,
        @Size(max = 2000, message = "Description must not exceed 2000 characters")
        String description,
        Status status,
        Priority priority,
        OffsetDateTime dueDate
) {
}
