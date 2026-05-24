package com.barbati.scheduledtask.repository;

import com.barbati.scheduledtask.model.ScheduledTask;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class ScheduledTaskRepository implements PanacheRepository<ScheduledTask>
{
    public List<ScheduledTask> findByTaskId(Long taskId)
    {
        return list("taskId", taskId);
    }
}
