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
        return ResponseEntity.ok("Task '" + taskEntity.getTitle() + "' created successfully. Id: " + taskEntity.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Long id, @RequestBody TaskEntity taskEntity) {
        taskService.updateTask(id, taskEntity);
        return ResponseEntity.ok("Task '" + taskEntity.getTitle() + "' updated successfully. Id: " + taskEntity.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task with Id '" + id + "' deleted successfully!");
    }

}
