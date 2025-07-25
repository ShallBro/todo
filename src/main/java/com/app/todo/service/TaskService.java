package com.app.todo.service;

import com.app.todo.dto.TaskDTO;
import com.app.todo.entity.Status;
import com.app.todo.entity.Task;
import com.app.todo.entity.User;
import com.app.todo.mapper.TaskMapper;
import com.app.todo.repository.StatusRepository;
import com.app.todo.repository.TaskRepository;
import com.app.todo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final StatusRepository statusRepository;

    private final UserRepository userRepository;
    private final TaskMapper taskMapper;
    public List<TaskDTO> getAll() {
        return taskMapper.toDTOList(taskRepository.findAll());
    }

    @Transactional
    public void create(TaskDTO taskDTO) {
        Status status = statusRepository.findByName(taskDTO.getStatus()).orElseThrow(RuntimeException::new);
        User user = userRepository.findByName(taskDTO.getUser()).orElseGet(() -> new User(taskDTO.getUser()));
        taskRepository.save(Task.builder()
                        .status(status)
                        .user(user)
                        .name(taskDTO.getName())
                        .description(taskDTO.getDescription())
                        .deadline(taskDTO.getDeadline())
                .build());
    }
}
