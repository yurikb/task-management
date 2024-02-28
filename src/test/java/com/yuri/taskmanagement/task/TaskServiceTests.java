package com.yuri.taskmanagement.task;

import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class TaskServiceTests {

    private TaskRepository taskRepository;
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskService(taskRepository);
    }

    @Test
    public void testGetAllTasks_ReturnsListOfTasks() {
        List<TaskEntity> mockTaskList = List.of(
                new TaskEntity("Task 1", "Description 1", LocalDate.parse("2024-01-01"), TaskStatus.DONE),
                new TaskEntity("Task 2", "Description 2", LocalDate.parse("2024-02-02"), TaskStatus.DONE)
        );
        when(taskRepository.findAll()).thenReturn(mockTaskList);

        List<TaskEntity> result = taskService.getAllTasks();

        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getTitle());
        assertEquals("Task 2", result.get(1).getTitle());
        assertEquals("Description 1", result.get(0).getDescription());
        assertEquals("Description 2", result.get(1).getDescription());
        assertEquals(LocalDate.parse("2024-01-01"), result.get(0).getDeadline());
        assertEquals(LocalDate.parse("2024-02-02"), result.get(1).getDeadline());
        assertEquals(TaskStatus.DONE, result.get(0).getStatus());
        assertEquals(TaskStatus.DONE, result.get(1).getStatus());
    }

    @Test
    public void testGetTaskById_ExistingId_ReturnsTask() {
        TaskEntity mockTask = new TaskEntity("Task 1", "Description 1", LocalDate.parse("2024-01-01"), TaskStatus.DONE);
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(mockTask));

        Optional<TaskEntity> result = taskService.getTaskById(1L);

        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());
    }

    @Test
    public void testGetTaskById_NonExistingId_ThrowsServiceException() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        // Act and Assert
        assertThrows(ServiceException.class, () -> taskService.getTaskById(1L));
    }

    @Test
    public void testCreateTask_ValidTask_CallsSaveMethod() {
        TaskEntity taskToCreate = new TaskEntity("New Task", "Description", LocalDate.parse("2024-03-03"), TaskStatus.TODO);
        taskService.createTask(taskToCreate);
        verify(taskRepository, times(1)).save(taskToCreate);
    }

    @Test
    public void createTask_NullTask_ThrowsServiceException() {
        assertThrows(ServiceException.class, () -> taskService.createTask(null));
    }

    @Test
    public void updateTask_ExistingTask_CallsSaveMethod() {
        TaskEntity existingTask = new TaskEntity("Task 1", "Description 1", LocalDate.parse("2024-01-01"), TaskStatus.DONE);
        when(taskRepository.existsById(1L)).thenReturn(true);
        taskService.updateTask(1L, existingTask);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    public void updateTask_NonExistingTask_ThrowsServiceException() {
        TaskEntity nonExistingTask = new TaskEntity("New Task", "Description", LocalDate.parse("2024-03-03"), TaskStatus.TODO);
        assertThrows(ServiceException.class, () -> taskService.updateTask(10L, nonExistingTask));
    }

    @Test
    public void deleteTask_ExistingTask_CallsDeleteMethod() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteTask_NonExistingTask_ThrowsServiceException() {
        when(taskRepository.existsById(1L)).thenReturn(false);

        // Act and Assert
        assertThrows(ServiceException.class, () -> taskService.deleteTask(1L));
    }

}
