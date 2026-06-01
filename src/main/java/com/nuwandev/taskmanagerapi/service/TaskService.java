package com.nuwandev.taskmanagerapi.service;

import com.nuwandev.taskmanagerapi.dto.request.CreateTaskRequest;
import com.nuwandev.taskmanagerapi.dto.request.UpdateTaskRequest;
import com.nuwandev.taskmanagerapi.dto.response.TaskPageResponse;
import com.nuwandev.taskmanagerapi.dto.response.TaskResponse;
import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;

import java.util.UUID;

public interface TaskService {
    UUID createTask(CreateTaskRequest request);

    TaskResponse getTaskById(UUID id);

    TaskResponse updateTask(UUID id, UpdateTaskRequest request);

    void deleteTask(UUID id);

    TaskPageResponse listTasks(int page, int size, String search, Status status, Priority priority, String sort);
}
