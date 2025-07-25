package com.app.todo.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TaskDTO {
    private String name;
    private String user;
    private String description;
    private Timestamp deadline;
    private String status;
}
