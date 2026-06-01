package com.nuwandev.taskmanagerapi.dto.response;

import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;

import java.time.OffsetDateTime;

public record TaskResponse(
        String id,
        String title,
        String description,
        Status status,
        Priority priority,
        OffsetDateTime dueDate,
        OffsetDateTime createdAt
) {
}

