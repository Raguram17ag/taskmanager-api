package com.taskapi.taskmanager.repository;

import com.taskapi.taskmanager.entity.Task;
import com.taskapi.taskmanager.entity.TaskPriority;
import com.taskapi.taskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Find all tasks for a specific user (with pagination)
    Page<Task> findByUserId(Long userId, Pageable pageable);

    // Find tasks by user and status
    Page<Task> findByUserIdAndStatus(Long userId, TaskStatus status, Pageable pageable);

    // Find tasks by user and priority
    Page<Task> findByUserIdAndPriority(Long userId, TaskPriority priority, Pageable pageable);

    // Count tasks for a user
    long countByUserId(Long userId);
}