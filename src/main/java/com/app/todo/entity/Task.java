package com.app.todo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private String name;
    private String description;
    private LocalDateTime deadline;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private StatusEntity statusEntity;
}
