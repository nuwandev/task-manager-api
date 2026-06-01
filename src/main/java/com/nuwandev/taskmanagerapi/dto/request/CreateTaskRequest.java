package com.nuwandev.taskmanagerapi.dto.request;

import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;

import java.time.OffsetDateTime;

public record CreateTaskRequest(
        String title,
        String description,
        Status status,
        Priority priority,
        OffsetDateTime dueDate
) {
}