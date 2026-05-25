package com.barbati.scheduledtask.service;

import com.barbati.scheduledtask.exception.ScheduledTaskException;
import com.barbati.scheduledtask.exception.ScheduledTaskUniquenessException;
import com.barbati.scheduledtask.model.ScheduledTask;
import com.barbati.scheduledtask.repository.ScheduledTaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ScheduledTaskService
{
    private final ScheduledTaskRepository scheduledTaskRepository;
    private final Validator validator;

    public ScheduledTaskService(ScheduledTaskRepository scheduledTaskRepository, Validator validator)
    {
        this.scheduledTaskRepository = scheduledTaskRepository;
        this.validator = validator;
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
        try
        {
            ScheduledTask existing = findById(scheduledTask.getId());

            if (existing == null)
                throw new ScheduledTaskException("Can't update a scheduled task that doesn't exist.");

            if (scheduledTask.getName() != null)
                existing.setName(scheduledTask.getName());

            if (scheduledTask.getDescription() != null)
                existing.setDescription(scheduledTask.getDescription());

            if (scheduledTask.getCron() != null)
                existing.setCron(scheduledTask.getCron());

            if (scheduledTask.getRequestParams() != null)
                existing.setRequestParams(scheduledTask.getRequestParams());

            if (scheduledTask.getRequestBody() != null)
                existing.setRequestBody(scheduledTask.getRequestBody());

            if (scheduledTask.getInternal() != null)
                existing.setInternal(scheduledTask.getInternal());

            Set<ConstraintViolation<ScheduledTask>> violations = validator.validate(existing);

            if (!violations.isEmpty())
                throw new ScheduledTaskException(
                        violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(", "))
                );

            return scheduledTaskRepository.getEntityManager().merge(existing);
        }
        catch (jakarta.validation.ConstraintViolationException e)
        {
            throw new ScheduledTaskException(e.getMessage(), e);
        }
        catch (ConstraintViolationException e)
        {
            throw new ScheduledTaskUniquenessException("Name must be unique. A scheduled task with that name already exists.", e);
        }
    }

    @Transactional
    public ScheduledTask create(ScheduledTask scheduledTask) throws ScheduledTaskException
    {
        try
        {
            scheduledTaskRepository.persist(scheduledTask);
            return scheduledTask;
        }
        catch (ConstraintViolationException e)
        {
            throw new ScheduledTaskUniquenessException("Name must be unique. A scheduled task with that name already exists.", e);
        }
    }

    @Transactional
    public void deleteById(Long id) throws ScheduledTaskException
    {
        if (scheduledTaskRepository.deleteById(id))
            return;

        throw new ScheduledTaskException("no scheduled task to delete");
    }
}
