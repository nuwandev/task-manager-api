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
}