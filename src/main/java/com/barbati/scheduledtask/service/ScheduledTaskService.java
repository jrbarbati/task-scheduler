package com.barbati.scheduledtask.service;

import com.barbati.scheduledtask.exception.ScheduledTaskException;
import com.barbati.scheduledtask.model.ScheduledTask;
import com.barbati.scheduledtask.repository.ScheduledTaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ScheduledTaskService
{
    private final ScheduledTaskRepository scheduledTaskRepository;

    public ScheduledTaskService(ScheduledTaskRepository scheduledTaskRepository)
    {
        this.scheduledTaskRepository = scheduledTaskRepository;
    }

    public List<ScheduledTask> findAll()
    {
        return scheduledTaskRepository.listAll();
    }

    public List<ScheduledTask> findByTaskId(Long taskId)
    {
        return scheduledTaskRepository.findByTaskId(taskId);
    }

    public ScheduledTask findById(Long id)
    {
        return scheduledTaskRepository.findById(id);
    }

    @Transactional
    public ScheduledTask update(ScheduledTask scheduledTask) throws ScheduledTaskException
    {
        ScheduledTask existing = findById(scheduledTask.getId());

        if (existing == null)
            throw new ScheduledTaskException("Can't update a scheduled task that doesn't exist.");

        return scheduledTaskRepository.getEntityManager().merge(scheduledTask);
    }

    @Transactional
    public ScheduledTask create(ScheduledTask scheduledTask)
    {
        scheduledTaskRepository.persist(scheduledTask);
        return scheduledTask;
    }

    @Transactional
    public void deleteById(Long id) throws ScheduledTaskException
    {
        if (scheduledTaskRepository.deleteById(id))
            return;

        throw new ScheduledTaskException("no scheduled task to delete");
    }
}
