package com.tasknest.tasknest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasknest.tasknest.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
