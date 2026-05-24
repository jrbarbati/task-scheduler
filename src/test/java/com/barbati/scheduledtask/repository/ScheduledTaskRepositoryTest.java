package com.barbati.scheduledtask.repository;

import com.barbati.scheduledtask.model.ScheduledTask;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
class ScheduledTaskRepositoryTest
{
    @Inject
    protected ScheduledTaskRepository scheduledTaskRepository;

    @Test
    @TestTransaction
    void persistAndFindByCode()
    {
        ScheduledTask scheduledTask = new ScheduledTask();

        scheduledTaskRepository.persist(scheduledTask);

        // asserts

        ScheduledTask found = scheduledTaskRepository.find("", "daily-report").firstResult();

        // asserts
    }

    @Test
    @TestTransaction
    void duplicateCodeRejected()
    {
        ScheduledTask first = new ScheduledTask();

        scheduledTaskRepository.persist(first);

        ScheduledTask second = new ScheduledTask();

        assertThrows(
                PersistenceException.class,
                () -> {
                    scheduledTaskRepository.persist(second);
                    scheduledTaskRepository.flush();
                }
        );
    }

    @Test
    @TestTransaction
    void softDeleteHidesFromFinders()
    {
        ScheduledTask task = new ScheduledTask();


        scheduledTaskRepository.persist(task);
        scheduledTaskRepository.flush();

        scheduledTaskRepository.delete(task);
        scheduledTaskRepository.flush();

        assertEquals(0, scheduledTaskRepository.find("code", "to-delete").count(),
                "soft-deleted task must not appear in queries");
    }
}