package com.app.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.app.todo.dto.TaskDTO;
import com.app.todo.entity.Status;
import com.app.todo.entity.Task;
import com.app.todo.entity.User;
import com.app.todo.mapper.TaskMapper;
import com.app.todo.repository.StatusRepository;
import com.app.todo.repository.TaskRepository;
import com.app.todo.repository.UserRepository;
import com.app.todo.service.TaskService;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

  @Mock
  private TaskMapper taskMapper;

  @Mock
  private TaskRepository taskRepository;

  @Mock
  private UserRepository userRepository;

  @Mock
  private StatusRepository statusRepository;

  @InjectMocks
  private TaskService taskService;

  @Test
  void listTaskDTO() {
    List<Task> tasks = new ArrayList<>();
    tasks.add(new Task());
    tasks.add(new Task());
    tasks.add(new Task());
    List<TaskDTO> taskDTOS = new ArrayList<>();
    taskDTOS.add(new TaskDTO());
    taskDTOS.add(new TaskDTO());
    taskDTOS.add(new TaskDTO());

    when(taskRepository.findAll()).thenReturn(tasks);
    when(taskMapper.toDTOList(tasks)).thenReturn(taskDTOS);

    assertEquals(3, taskService.getAll().size());
  }

  @Test
  void update() {
    TaskDTO dto = new TaskDTO();
    dto.setUser("Kolya");
    dto.setStatus("todo");
    Task task = new Task();
    task.setUser(new User("Tema"));
    task.setStatus(new Status("in progress"));
    long id = 1;

    when(taskRepository.findById(id)).thenReturn(Optional.of(task));
    when(userRepository.findByName(dto.getUser())).thenReturn(Optional.of(new User("Kolya")));
    when(statusRepository.findByName(dto.getStatus())).thenReturn(Optional.of(new Status("todo")));

    taskService.update(id, dto);

    assertEquals("Kolya", task.getUser().getName());
    assertEquals("todo", task.getStatus().getName());

  }

  @Test
  void filterByStatus() {
    Task task1 = new Task();
    task1.setStatus(new Status("todo"));
    Task task2 = new Task();
    task2.setStatus(new Status("in progress"));
    TaskDTO taskDTO = new TaskDTO();
    taskDTO.setStatus("todo");
    List<Task> tasks = List.of(task1, task2);
    List<TaskDTO> taskDTOS = List.of(taskDTO);
    Status statusEntity = new Status("todo");

    when(statusRepository.findByName("todo")).thenReturn(Optional.of(statusEntity));
    when(taskRepository.findByStatus(statusEntity)).thenReturn(tasks);
    when(taskMapper.toDTOList(tasks)).thenReturn(taskDTOS);

    List<TaskDTO> result = taskService.filterByStatus("todo");

    assertEquals(taskDTOS, result);

  }

  @Test
  void create() {
    TaskDTO taskDTO = new TaskDTO();
    taskDTO.setStatus("todo");
    taskDTO.setUser("Artem");
    Status statusEntity = new Status("todo");
    Task task = new Task();
    task.setStatus(new Status("todo"));
    task.setUser(new User("Artem"));

    when(statusRepository.findByName("todo")).thenReturn(Optional.of(statusEntity));
    when(userRepository.findByName(taskDTO.getUser())).thenReturn(Optional.of(new User("Artem")));
    when(taskRepository.save(any(Task.class))).thenReturn(task);

    taskService.create(taskDTO);

    verify(taskRepository).save(any(Task.class));
  }

  @Test
  void sortByDeadline() {
    TaskDTO t1 = new TaskDTO();
    t1.setDeadline(Timestamp.valueOf(LocalDateTime.of(2025,1,1,10,0)));
    TaskDTO t2 = new TaskDTO();
    t2.setDeadline(Timestamp.valueOf(LocalDateTime.of(2025,1,1,12,0)));
    TaskDTO t3 = new TaskDTO();
    t3.setDeadline(Timestamp.valueOf(LocalDateTime.of(2025,1,1,8,0)));
    List<TaskDTO> unsorted = List.of(t1, t2, t3);
    List<Task> entities = List.of(new Task(), new Task(), new Task());

    when(taskRepository.findAll()).thenReturn(entities);
    when(taskMapper.toDTOList(entities)).thenReturn(unsorted);

    List<TaskDTO> sorted = taskService.sortByDeadline();

    assertEquals(List.of(t2, t1, t3), sorted);
  }

  @Test
  void delete() {
    long id = 1;

    taskService.delete(id);

    verify(taskRepository).deleteById(id);
  }

}
