package com.app.todo.controller;

import com.app.todo.dto.TaskDTO;
import com.app.todo.service.TaskService;
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
    public void createTask(@RequestBody TaskDTO taskDTO) {
        taskService.create(taskDTO);
    }

    @PutMapping
    public void updateTask(@RequestParam int id, @RequestBody TaskDTO taskDTO) {
        taskService.update(id, taskDTO);
    }

    @DeleteMapping
    public void deleteTask(@RequestParam int id) {
        taskService.delete(id);
    }
}
