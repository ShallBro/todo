package com.app.todo.service;

import com.app.todo.dto.TaskDTO;
import com.app.todo.entity.StatusEntity;
import com.app.todo.entity.Task;
import com.app.todo.entity.User;
import com.app.todo.enums.Status;
import com.app.todo.mapper.TaskMapper;
import com.app.todo.repository.StatusRepository;
import com.app.todo.repository.TaskRepository;
import com.app.todo.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Comparator;
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
        Task task = new Task();
        StatusEntity statusEntity = statusRepository.findByName(taskDTO.getStatus().name()).orElseThrow(RuntimeException::new);
        User user = userRepository.findByName(taskDTO.getUser()).orElseGet(() -> new User(taskDTO.getUser()));
        task.setUser(user);
        task.setName(taskDTO.getName());
        task.setDeadline(taskDTO.getDeadline());
        task.setStatusEntity(statusEntity);
        task.setDescription(taskDTO.getDescription());
        taskRepository.save(task);
    }
    @Transactional
    public void update(long id, TaskDTO taskDTO) {
        Task task = taskRepository.findById(id).orElseThrow();
        User user = userRepository.findByName(taskDTO.getUser()).orElseGet(() -> new User(taskDTO.getUser()));
        StatusEntity statusEntity = statusRepository.findByName(taskDTO.getStatus().name()).orElseThrow(RuntimeException::new);
        task.setUser(user);
        task.setName(taskDTO.getName());
        task.setDeadline(taskDTO.getDeadline());
        task.setStatusEntity(statusEntity);
        task.setDescription(taskDTO.getDescription());
    }

    public List<TaskDTO> filterByStatus(Status status) {
        StatusEntity statusEntity = statusRepository.findByName(status.name()).orElseThrow(RuntimeException::new);
        return taskMapper.toDTOList(taskRepository.findByStatusEntity(statusEntity));
    }

    public List<TaskDTO> sortByDeadline() {
      return taskMapper.toDTOList(taskRepository.findAll()).stream()
        .sorted(Comparator.comparing(TaskDTO::getDeadline).reversed())
        .toList();
    }

    public List<TaskDTO> sortByStatus() {
        return taskMapper.toDTOList(taskRepository.findAll()).stream()
          .sorted(Comparator.comparing(TaskDTO::getStatus).reversed())
          .toList();
    }

    public void delete(long id) {
        taskRepository.deleteById(id);
    }
}
