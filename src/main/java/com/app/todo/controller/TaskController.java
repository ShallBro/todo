package com.app.todo.controller;

import com.app.todo.dto.TaskDTO;
import com.app.todo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/task")
public class TaskController {
    private final TaskService taskService;
    @GetMapping("/list_tasks")
    public List<TaskDTO> getListTasks() {
        return taskService.getAll();
    }

    @PostMapping("/create")
    public void createTask(@RequestBody TaskDTO taskDTO) {
        taskService.create(taskDTO);
    }
}
