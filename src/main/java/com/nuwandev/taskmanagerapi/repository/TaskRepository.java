package com.nuwandev.taskmanagerapi.repository;

import com.nuwandev.taskmanagerapi.entity.Task;

import java.util.UUID;

public interface TaskRepository {
    UUID save(Task task);

    Task getById(UUID id);

    void update(Task task);
}