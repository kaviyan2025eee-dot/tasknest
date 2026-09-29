package com.tasknest.tasknest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tasknest.tasknest.entity.TaskList;
import com.tasknest.tasknest.service.TaskListService;

@RestController
@RequestMapping("/api/task-lists")
@CrossOrigin(origins = "*")
public class TaskListController {

    private final TaskListService taskListService;

    public TaskListController(TaskListService taskListService) {
        this.taskListService = taskListService;
    }

    // Create Task List
    @PostMapping
    public ResponseEntity<TaskList> createTaskList(@RequestBody TaskList taskList) {
        TaskList savedTaskList = taskListService.createTaskList(taskList);
        return new ResponseEntity<>(savedTaskList, HttpStatus.CREATED);
    }

    // Get all Task Lists
    @GetMapping
    public ResponseEntity<List<TaskList>> getAllTaskLists() {
        return ResponseEntity.ok(taskListService.getAllTaskLists());
    }

    // Get Task List by ID
    @GetMapping("/{id}")
    public ResponseEntity<TaskList> getTaskListById(@PathVariable Long id) {
        return ResponseEntity.ok(taskListService.getTaskListById(id));
    }

    // Update Task List
    @PutMapping("/{id}")
    public ResponseEntity<TaskList> updateTaskList(
            @PathVariable Long id,
            @RequestBody TaskList taskList) {

        return ResponseEntity.ok(
                taskListService.updateTaskList(id, taskList)
        );
    }

    // Delete Task List
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaskList(@PathVariable Long id) {
        taskListService.deleteTaskList(id);
        return ResponseEntity.noContent().build();
    }
}
