package com.tasknest.tasknest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tasknest.tasknest.entity.TaskList;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {

}
