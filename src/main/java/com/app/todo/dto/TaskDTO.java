package com.app.todo.dto;

import com.app.todo.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskDTO {
  @NotBlank(message = "Название задачи обязательно")
  @Size(max = 250, message = "Не более 250 символов")
  private String name;

  @NotBlank(message = "Пользователь обязателен")
  private String user;

  @Size(max = 500, message = "Описание слишком длинное (макс 500)")
  private String description;
  private LocalDateTime deadline;

  @NotNull(message = "Статус обязателен")
  private Status status;
}
