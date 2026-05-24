package com.barbati.scheduledtask.service;

import com.barbati.scheduledtask.model.ScheduledTask;
import com.barbati.scheduledtask.repository.ScheduledTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledTaskServiceTest
{
    private ScheduledTaskService scheduledTaskService;

    @Mock
    private ScheduledTaskRepository mockScheduledTaskRepository;

    @BeforeEach
    void setUp()
    {
        scheduledTaskService = spy(new ScheduledTaskService(mockScheduledTaskRepository));
    }

    @Test
    void findAll()
    {
        when(mockScheduledTaskRepository.listAll()).thenReturn(new ArrayList<>());

        List<ScheduledTask> scheduledTasks = scheduledTaskService.findAll();

        assertNotNull(scheduledTasks);

        verify(mockScheduledTaskRepository, times(1)).listAll();
    }

    @Test
    void findByTaskId()
    {
        when(mockScheduledTaskRepository.findByTaskId(1L)).thenReturn(new ArrayList<>());

        List<ScheduledTask> scheduledTasks = scheduledTaskService.findByTaskId(1L);

        assertNotNull(scheduledTasks);

        verify(mockScheduledTaskRepository, times(1)).findByTaskId(1L);
    }

    @Test
    void findById()
    {
        when(mockScheduledTaskRepository.findById(1L)).thenReturn(new ScheduledTask());

        ScheduledTask scheduledTask = scheduledTaskService.findById(1L);

        assertNotNull(scheduledTask);

        verify(mockScheduledTaskRepository, times(1)).findById(1L);
    }
}