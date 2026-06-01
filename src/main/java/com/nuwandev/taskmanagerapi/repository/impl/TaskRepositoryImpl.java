package com.nuwandev.taskmanagerapi.repository.impl;

import com.nuwandev.taskmanagerapi.dto.response.TaskPageResponse;
import com.nuwandev.taskmanagerapi.dto.response.TaskResponse;
import com.nuwandev.taskmanagerapi.entity.Task;
import com.nuwandev.taskmanagerapi.enums.Priority;
import com.nuwandev.taskmanagerapi.enums.Status;
import com.nuwandev.taskmanagerapi.repository.TaskRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private static final String BASE_SELECT = "SELECT id, title, description, status, priority, due_date, created_at, updated_at FROM tasks";
    private static final String BASE_COUNT = "SELECT COUNT(*) FROM tasks";
    private static final String DEFAULT_SORT_COLUMN = "created_at";
    private static final String DEFAULT_SORT_DIRECTION = "DESC";

    private static final RowMapper<Task> TASK_ROW_MAPPER = (resultSet, rowNum) -> {
        Task task = new Task();
        task.setId(resultSet.getObject("id", UUID.class));
        task.setTitle(resultSet.getString("title"));
        task.setDescription(resultSet.getString("description"));
        task.setStatus(Status.valueOf(resultSet.getString("status")));
        task.setPriority(Priority.valueOf(resultSet.getString("priority")));
        task.setDueDate(resultSet.getObject("due_date", OffsetDateTime.class));
        task.setCreatedAt(resultSet.getObject("created_at", OffsetDateTime.class));
        task.setUpdatedAt(resultSet.getObject("updated_at", OffsetDateTime.class));
        return task;
    };

    final private JdbcTemplate jdbcTemplate;

    public TaskRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UUID save(Task task) {
        jdbcTemplate.update("""
                        INSERT INTO tasks (id, title, description, status, priority, due_date, created_at, updated_at)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                        """,
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );

        return task.getId();
    }

    @Override
    public Task getById(UUID id) {
        try {
            return jdbcTemplate.queryForObject(BASE_SELECT + " WHERE id = ?", TASK_ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public TaskPageResponse listTasks(int page, int size, String search, Status status, Priority priority, String sort) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.max(size, 1);

        List<String> clauses = new ArrayList<>();
        List<Object> parameters = new ArrayList<>();

        if (search != null && !search.isBlank()) {
            clauses.add("(title ILIKE ? ESCAPE '\\' OR description ILIKE ? ESCAPE '\\')");
            String likePattern = "%" + search.trim()
                    .replace("\\", "\\\\")
                    .replace("%", "\\%")
                    .replace("_", "\\_") + "%";
            parameters.add(likePattern);
            parameters.add(likePattern);
        }

        if (status != null) {
            clauses.add("status = ?");
            parameters.add(status.name());
        }

        if (priority != null) {
            clauses.add("priority = ?");
            parameters.add(priority.name());
        }

        String whereClause = clauses.isEmpty() ? "" : " WHERE " + String.join(" AND ", clauses);

        String sortColumn = DEFAULT_SORT_COLUMN;
        String sortDirection = DEFAULT_SORT_DIRECTION;
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.trim().split(",", 2);
            String requestedColumn = parts[0].trim();
            String requestedDirection = parts.length > 1 ? parts[1].trim().toUpperCase(Locale.ROOT) : DEFAULT_SORT_DIRECTION;

            sortColumn = switch (requestedColumn) {
                case "title" -> "title";
                case "description" -> "description";
                case "status" -> "status";
                case "priority" -> "priority";
                case "dueDate", "due_date" -> "due_date";
                case "createdAt", "created_at" -> "created_at";
                case "updatedAt", "updated_at" -> "updated_at";
                default -> DEFAULT_SORT_COLUMN;
            };

            sortDirection = "ASC".equals(requestedDirection) || "DESC".equals(requestedDirection)
                    ? requestedDirection
                    : DEFAULT_SORT_DIRECTION;
        }

        Long totalElementsValue = jdbcTemplate.queryForObject(
                BASE_COUNT + whereClause,
                Long.class,
                parameters.toArray()
        );
        long totalElements = totalElementsValue == null ? 0L : totalElementsValue;

        List<Object> contentParameters = new ArrayList<>(parameters);
        contentParameters.add(safeSize);
        contentParameters.add((long) safePage * safeSize);

        String contentSql = BASE_SELECT + whereClause + " ORDER BY " + sortColumn + " " + sortDirection + ", created_at DESC LIMIT ? OFFSET ?";

        List<TaskResponse> content = jdbcTemplate.query(
                contentSql,
                (resultSet, rowNum) -> {
                    Task task = TASK_ROW_MAPPER.mapRow(resultSet, rowNum);
                    return new TaskResponse(
                            task.getId() == null ? null : task.getId().toString(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getStatus(),
                            task.getPriority(),
                            task.getDueDate(),
                            task.getCreatedAt()
                    );
                },
                contentParameters.toArray()
        );

        int totalPages = (int) Math.ceil((double) totalElements / safeSize);

        return new TaskPageResponse(content, safePage, safeSize, totalElements, totalPages);
    }

    @Override
    public void update(Task task) {
        jdbcTemplate.update("""
                        UPDATE tasks
                        SET title = ?,
                            description = ?,
                            status = ?,
                            priority = ?,
                            due_date = ?,
                            updated_at = ?
                        WHERE id = ?
                        """,
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getPriority().name(),
                task.getDueDate(),
                task.getUpdatedAt(),
                task.getId()
        );
    }

    @Override
    public void delete(UUID id) {
        jdbcTemplate.update("DELETE FROM tasks WHERE id = ?", id);
    }
}
