package com.barbati.task.controller;

import com.barbati.task.model.Task;
import com.barbati.task.service.TaskService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/v1")
@Produces(MediaType.APPLICATION_JSON)
public class TaskController
{
    private final TaskService taskService;

    public TaskController(TaskService taskService)
    {
        this.taskService = taskService;
    }

    @GET
    @Path("/tasks")
    public List<Task> fetch(@QueryParam("active") Boolean active)
    {
        return taskService.findAll();
    }
}
