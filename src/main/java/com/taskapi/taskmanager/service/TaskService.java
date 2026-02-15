package com.taskapi.taskmanager.service;

import com.taskapi.taskmanager.dto.TaskRequest;
import com.taskapi.taskmanager.dto.TaskResponse;
import com.taskapi.taskmanager.entity.Task;
import com.taskapi.taskmanager.entity.TaskPriority;
import com.taskapi.taskmanager.entity.TaskStatus;
import com.taskapi.taskmanager.entity.User;
import com.taskapi.taskmanager.exception.ResourceNotFoundException;
import com.taskapi.taskmanager.exception.UnauthorizedException;
import com.taskapi.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;

    public TaskResponse createTask(TaskRequest request, String username) {
        User user = userService.findByUsername(username);

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus() != null ? request.getStatus() : TaskStatus.PENDING);
        task.setPriority(request.getPriority() != null ? request.getPriority() : TaskPriority.MEDIUM);
        task.setDueDate(request.getDueDate());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    public Page<TaskResponse> getAllTasksForUser(String username, Pageable pageable) {
        User user = userService.findByUsername(username);
        Page<Task> tasks = taskRepository.findByUserId(user.getId(), pageable);
        return tasks.map(this::mapToResponse);
    }

    public Page<TaskResponse> getTasksByStatus(String username, TaskStatus status, Pageable pageable) {
        User user = userService.findByUsername(username);
        Page<Task> tasks = taskRepository.findByUserIdAndStatus(user.getId(), status, pageable);
        return tasks.map(this::mapToResponse);
    }

    public Page<TaskResponse> getTasksByPriority(String username, TaskPriority priority, Pageable pageable) {
        User user = userService.findByUsername(username);
        Page<Task> tasks = taskRepository.findByUserIdAndPriority(user.getId(), priority, pageable);
        return tasks.map(this::mapToResponse);
    }

    public TaskResponse getTaskById(Long taskId, String username) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        // Check if task belongs to the user
        if (!task.getUser().getUsername().equals(username)) {
            throw new UnauthorizedException("You don't have permission to access this task");
        }

        return mapToResponse(task);
    }

    public TaskResponse updateTask(Long taskId, TaskRequest request, String username) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        // Check if task belongs to the user
        if (!task.getUser().getUsername().equals(username)) {
            throw new UnauthorizedException("You don't have permission to update this task");
        }

        // Update fields
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        if (request.getPriority() != null) {
            task.setPriority(request.getPriority());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }

        Task updatedTask = taskRepository.save(task);
        return mapToResponse(updatedTask);
    }

    public void deleteTask(Long taskId, String username) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        // Check if task belongs to the user
        if (!task.getUser().getUsername().equals(username)) {
            throw new UnauthorizedException("You don't have permission to delete this task");
        }

        taskRepository.delete(task);
    }

    // Helper method to convert Task entity to TaskResponse DTO
    private TaskResponse mapToResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}