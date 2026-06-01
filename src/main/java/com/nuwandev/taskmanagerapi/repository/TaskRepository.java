package com.nuwandev.taskmanagerapi.repository;

import com.nuwandev.taskmanagerapi.dto.response.TaskPageResponse;
import com.nuwandev.taskmanagerapi.entity.Task;
import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;

import java.util.UUID;

public interface TaskRepository {
    UUID save(Task task);

    Task getById(UUID id);

    TaskPageResponse listTasks(int page, int size, String search, Status status, Priority priority, String sort);

    void update(Task task);

    void delete(UUID id);
}