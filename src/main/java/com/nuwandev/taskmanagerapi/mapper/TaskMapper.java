package com.nuwandev.taskmanagerapi.mapper;

import com.nuwandev.taskmanagerapi.dto.response.TaskResponse;
import com.nuwandev.taskmanagerapi.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskResponse toDto(Task task);
}
