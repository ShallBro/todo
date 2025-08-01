package com.app.todo.repository;

import com.app.todo.entity.StatusEntity;
import com.app.todo.entity.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatusEntity(StatusEntity statusEntity);
}
