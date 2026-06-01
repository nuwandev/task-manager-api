package com.nuwandev.taskmanagerapi.service.impl;

import com.nuwandev.taskmanagerapi.dto.request.CreateTaskRequest;
import com.nuwandev.taskmanagerapi.dto.request.UpdateTaskRequest;
import com.nuwandev.taskmanagerapi.dto.response.TaskPageResponse;
import com.nuwandev.taskmanagerapi.dto.response.TaskResponse;
import com.nuwandev.taskmanagerapi.entity.Task;
import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;
import com.nuwandev.taskmanagerapi.exceptions.TaskNotFoundException;
import com.nuwandev.taskmanagerapi.mapper.TaskMapper;
import com.nuwandev.taskmanagerapi.repository.TaskRepository;
import com.nuwandev.taskmanagerapi.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    final private TaskRepository taskRepository;
    final private TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public UUID createTask(CreateTaskRequest request) {
        Task task = new Task();

        task.setId(UUID.randomUUID());
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status() != null ? request.status() : Status.TODO);
        task.setPriority(request.priority() != null ? request.priority() : Priority.MEDIUM);
        task.setDueDate(request.dueDate());
        task.setCreatedAt(OffsetDateTime.now());
        task.setUpdatedAt(OffsetDateTime.now());

        return taskRepository.save(task);
    }

    @Override
    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.getById(id);
        if (task == null) {
            throw new TaskNotFoundException();
        }
        return taskMapper.toDto(task);
    }

    @Override
    public TaskResponse updateTask(UUID id, UpdateTaskRequest request) {
        
    }

    @Override
    public void deleteTask(UUID id) {

    }

    @Override
    public TaskPageResponse listTasks(int page, int size, String search, Status status, Priority priority, String sort) {

    }
}
