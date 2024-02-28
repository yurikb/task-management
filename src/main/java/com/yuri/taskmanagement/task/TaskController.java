package com.yuri.taskmanagement.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskEntity> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Optional<TaskEntity> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskEntity taskEntity) {
        taskService.createTask(taskEntity);
        return ResponseEntity.ok("Task " + taskEntity.getTitle() + " created successfully. Id: " + taskEntity.getId());
    }

    @PutMapping("/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody TaskEntity taskEntity) {
        taskService.updateTask(id, taskEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

}
