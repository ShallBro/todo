package com.app.todo.mapper;

import com.app.todo.dto.TaskDTO;
import com.app.todo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "status", source = "statusEntity.name")
    @Mapping(target = "user", source = "user.name")
    TaskDTO toDTO(Task task);

    List<TaskDTO> toDTOList(List<Task> tasks);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "statusEntity.name", source = "status")
    @Mapping(target = "user.name", source = "user")
    Task toEntity(TaskDTO taskDTO);

}
