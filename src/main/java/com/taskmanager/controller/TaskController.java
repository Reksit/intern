package com.taskmanager.controller;

import com.taskmanager.model.Task;
import com.taskmanager.model.User;
import com.taskmanager.repository.UserRepository;
import com.taskmanager.service.JwtUtil;
import com.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public List<Task> getTasks(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName());
        return taskService.getTasksByUser(user);
    }

    @PostMapping
    public Task createTask(Authentication auth, @RequestBody Task task) {
        User user = userRepository.findByUsername(auth.getName());
        task.setUser(user);
        return taskService.save(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(Authentication auth, @PathVariable Long id, @RequestBody Task updatedTask) {
        User user = userRepository.findByUsername(auth.getName());
        updatedTask.setId(id);
        updatedTask.setUser(user);
        return taskService.update(updatedTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.delete(id);
    }
}
