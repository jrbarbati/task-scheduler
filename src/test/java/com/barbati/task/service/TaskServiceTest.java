package com.barbati.task.service;

import com.barbati.task.model.Task;
import com.barbati.task.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest
{
    private TaskService taskService;

    @Mock
    private TaskRepository mocKTaskRepository;

    @BeforeEach
    void setUp()
    {
        taskService = spy(new TaskService(mocKTaskRepository));
    }

    @Test
    void findAll()
    {
        when(mocKTaskRepository.listAll()).thenReturn(new ArrayList<>());

        List<Task> tasks = taskService.findAll();

        assertNotNull(tasks);

        verify(mocKTaskRepository, times(1)).listAll();
    }
}