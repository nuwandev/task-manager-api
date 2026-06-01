package com.nuwandev.taskmanagerapi.controller;

import com.nuwandev.taskmanagerapi.dto.request.CreateTaskRequest;
import com.nuwandev.taskmanagerapi.dto.request.UpdateTaskRequest;
import com.nuwandev.taskmanagerapi.dto.response.ApiResponse;
import com.nuwandev.taskmanagerapi.dto.response.TaskIdResponse;
import com.nuwandev.taskmanagerapi.dto.response.TaskPageResponse;
import com.nuwandev.taskmanagerapi.dto.response.TaskResponse;
import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;
import com.nuwandev.taskmanagerapi.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    final private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskIdResponse>> createTask(@RequestBody @Valid CreateTaskRequest request) {
        UUID taskId = taskService.createTask(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Task created successfully", new TaskIdResponse(taskId.toString())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getTask(@PathVariable UUID id) {
        return ResponseEntity.ok(new ApiResponse<>(true, null, taskService.getTaskById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> updateTask(@PathVariable UUID id, @RequestBody @Valid UpdateTaskRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Task updated successfully", taskService.updateTask(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Task deleted successfully", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TaskPageResponse>> listTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        return ResponseEntity.ok(new ApiResponse<>(true, null, taskService.listTasks(page, size, search, status, priority, sort)));
    }
}

