package com.yuri.taskmanagement.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public TaskEntity() {}

    public TaskEntity(String title, String description, LocalDate deadline, TaskStatus status) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity taskEntity = (TaskEntity) o;
        return Objects.equals(id, taskEntity.id) && Objects.equals(title, taskEntity.title) && Objects.equals(description, taskEntity.description) && Objects.equals(deadline, taskEntity.deadline) && Objects.equals(status, taskEntity.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, deadline, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", status='" + status + '\'' +
                '}';
    }
}
