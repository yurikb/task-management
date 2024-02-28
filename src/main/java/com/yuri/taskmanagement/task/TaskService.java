package com.yuri.taskmanagement.task;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Optional<TaskEntity> getTaskById(Long id) {
        return Optional.of(taskRepository.findById(id).orElseThrow(() -> new ServiceException("Task with id " + id + " not found.")));
    }

    @Transactional
    public void createTask(TaskEntity taskEntity) {
        if (taskEntity == null) {
            throw new ServiceException("Error: Task cannot be null");
        }

        log.info("Creating Task: {}", taskEntity);
        taskRepository.save(taskEntity);
        log.info("Task '{}' created successfully!", taskEntity.getTitle());
    }

    @Transactional
    public void updateTask(Long id, TaskEntity updatedTaskEntity) {
        if (taskRepository.existsById(id)) {
            log.info("Updating Task: {}", updatedTaskEntity);
            updatedTaskEntity.setId(id);
            taskRepository.save(updatedTaskEntity);
            log.info("Task updated successfully: {}", updatedTaskEntity);
        } else {
            throw new ServiceException("Task with id " + id + " doesn't exist.");
        }
    }

    @Transactional
    public void deleteTask(Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new ServiceException("Task with id " + id + " not found.");
        }
    }

}
