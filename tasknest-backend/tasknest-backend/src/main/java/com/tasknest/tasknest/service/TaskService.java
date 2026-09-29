package com.tasknest.tasknest.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tasknest.tasknest.entity.Task;
import com.tasknest.tasknest.repository.TaskListRepository;
import com.tasknest.tasknest.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskService(
            TaskRepository taskRepository,
            TaskListRepository taskListRepository) {

        this.taskRepository = taskRepository;
        this.taskListRepository = taskListRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    public Task updateTask(Long id, Task updatedTask) {

        Task task = getTaskById(id);

        task.setTitle(updatedTask.getTitle());
        task.setDueDate(updatedTask.getDueDate());
        task.setPriority(updatedTask.getPriority());
        task.setTaskList(updatedTask.getTaskList());

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task completeTask(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(true);
        return taskRepository.save(task);
    }

    public Task incompleteTask(Long id) {
        Task task = getTaskById(id);
        task.setCompleted(false);
        return taskRepository.save(task);
    }

    public List<Task> getTodayTasks() {

        LocalDate today = LocalDate.now();

        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getDueDate() != null)
                .filter(task -> task.getDueDate().equals(today))
                .toList();
    }

    public List<Task> getOverdueTasks() {

        LocalDate today = LocalDate.now();

        return taskRepository.findAll()
                .stream()
                .filter(task -> task.getDueDate() != null)
                .filter(task -> task.getDueDate().isBefore(today))
                .filter(task -> !task.isCompleted())
                .toList();
    }

    // Move task from one list to another
    public Task moveTask(Long taskId, Long taskListId) {

        Task task = getTaskById(taskId);

        var taskList = taskListRepository.findById(taskListId)
                .orElseThrow(() -> new RuntimeException("Task List not found"));

        task.setTaskList(taskList);

        return taskRepository.save(task);
    }
}