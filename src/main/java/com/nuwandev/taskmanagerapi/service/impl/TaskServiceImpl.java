package com.nuwandev.taskmanagerapi.service.impl;

import com.nuwandev.taskmanagerapi.dto.request.CreateTaskRequest;
import com.nuwandev.taskmanagerapi.dto.request.UpdateTaskRequest;
import com.nuwandev.taskmanagerapi.dto.response.TaskPageResponse;
import com.nuwandev.taskmanagerapi.dto.response.TaskResponse;
import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;
import com.nuwandev.taskmanagerapi.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public UUID createTask(CreateTaskRequest request) {
        return null;
    }

    @Override
    public TaskResponse getTaskById(UUID id) {
        return null;
    }

    @Override
    public TaskResponse updateTask(UUID id, UpdateTaskRequest request) {
        return null;
    }

    @Override
    public void deleteTask(UUID id) {

    }

    @Override
    public TaskPageResponse listTasks(int page, int size, String search, Status status, Priority priority, String sort) {
        return null;
    }
}
