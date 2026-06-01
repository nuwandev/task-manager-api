package com.nuwandev.taskmanagerapi.entity;

import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;

import java.sql.Timestamp;
import java.util.UUID;

public class Task {
    private UUID id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private Timestamp dueDate;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Task() {}

    public Task(UUID id, String title, String description, Status status, Priority priority, Timestamp dueDate, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public Timestamp getDueDate() {
        return dueDate;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}