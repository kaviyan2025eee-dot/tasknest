package com.tasknest.tasknest.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tasknest.tasknest.entity.TaskList;
import com.tasknest.tasknest.repository.TaskListRepository;

@Service
public class TaskListService {

    private final TaskListRepository taskListRepository;

    public TaskListService(TaskListRepository taskListRepository) {
        this.taskListRepository = taskListRepository;
    }

    public TaskList createTaskList(TaskList taskList) {
        return taskListRepository.save(taskList);
    }

    public List<TaskList> getAllTaskLists() {
        return taskListRepository.findAll();
    }

    public TaskList getTaskListById(Long id) {
        return taskListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task List not found"));
    }

    public TaskList updateTaskList(Long id, TaskList updatedTaskList) {

        TaskList taskList = getTaskListById(id);

        taskList.setName(updatedTaskList.getName());

        return taskListRepository.save(taskList);
    }

    public void deleteTaskList(Long id) {
        taskListRepository.deleteById(id);
    }
}
