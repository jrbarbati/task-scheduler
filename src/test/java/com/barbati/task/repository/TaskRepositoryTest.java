package com.barbati.task.repository;

import com.barbati.task.model.HttpMethod;
import com.barbati.task.model.Task;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TaskRepositoryTest
{
    @Inject
    protected TaskRepository taskRepository;

    @Test
    @TestTransaction
    void persistAndFindByCode()
    {
        Task task = new Task();
        task.setCode("daily-report");
        task.setName("Daily Report");
        task.setDescription("Triggers the daily report job");
        task.setUrl("https://example.test/hooks/daily-report");
        task.setMethod(HttpMethod.POST);

        taskRepository.persist(task);

        assertNotNull(task.getId(), "id should be populated after persist");
        assertNotNull(task.getCreatedAt(), "createdAt auto-managed");
        assertNotNull(task.getUpdatedAt(), "updatedAt auto-managed");

        Task found = taskRepository.find("code", "daily-report").firstResult();
        assertEquals(task.getId(), found.getId());
        assertEquals(HttpMethod.POST, found.getMethod());
    }

    @Test
    @TestTransaction
    void duplicateCodeRejected()
    {
        Task first = new Task();
        first.setCode("dup-code");
        first.setName("First");
        first.setUrl("https://example.test/a");
        first.setMethod(HttpMethod.GET);
        taskRepository.persist(first);

        Task second = new Task();
        second.setCode("dup-code");
        second.setName("Second");
        second.setUrl("https://example.test/b");
        second.setMethod(HttpMethod.GET);

        assertThrows(
            PersistenceException.class,
            () -> {
                taskRepository.persist(second);
                taskRepository.flush();
            }
        );
    }

    @Test
    @TestTransaction
    void softDeleteHidesFromFinders()
    {
        Task task = new Task();
        task.setCode("to-delete");
        task.setName("Goes Away");
        task.setUrl("https://example.test/gone");
        task.setMethod(HttpMethod.DELETE);
        taskRepository.persist(task);
        taskRepository.flush();

        taskRepository.delete(task);
        taskRepository.flush();

        assertEquals(0, taskRepository.find("code", "to-delete").count(),
            "soft-deleted task must not appear in queries");
    }
}
