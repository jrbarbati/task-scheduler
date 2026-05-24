package com.barbati.task.service;

import com.barbati.task.model.Task;
import com.barbati.task.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TaskService
{
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository)
    {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll()
    {
        return taskRepository.listAll();
    }
}
