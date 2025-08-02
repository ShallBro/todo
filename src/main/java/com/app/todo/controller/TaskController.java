package com.app.todo.controller;

import com.app.todo.dto.TaskDTO;
import com.app.todo.enums.Status;
import com.app.todo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/tasks")
public class TaskController {
    private final TaskService taskService;
    @GetMapping
    public List<TaskDTO> getListTasks() {
        return taskService.getAll();
    }

    @PostMapping
    public void createTask(@Valid @RequestBody TaskDTO taskDTO) {
        taskService.create(taskDTO);
    }

    @PutMapping
    public void updateTask(@RequestParam int id, @Valid @RequestBody TaskDTO taskDTO) {
        taskService.update(id, taskDTO);
    }

    @DeleteMapping
    public void deleteTask(@RequestParam int id) {
        taskService.delete(id);
    }

    @GetMapping("/filter/status")
    public List<TaskDTO> filterStatus(@RequestParam Status name) {
        return taskService.filterByStatus(name);
    }

    @GetMapping("/sorted/deadline")
    public List<TaskDTO> sortByDeadline() {
        return taskService.sortByDeadline();
    }

    @GetMapping("/sorted/status")
    public List<TaskDTO> sortByStatus() {
        return taskService.sortByStatus();
    }
}
