package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static java.util.Optional.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final String TITLE_1 = "test_title_1";
    private static final String TITLE_2 = "test_title_2";
    private static final String CONTENT_1 = "test content 1";
    private static final String CONTENT_2 = "test content 1";

    @InjectMocks
    DbService service;

    @Mock
    TaskRepository repository;

    @Test
    public void testGetAllTasks() {
        //Given
        Task task1 = new Task(ID_1, TITLE_1, CONTENT_1);
        Task task2 = new Task(ID_2, TITLE_2, CONTENT_2);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        when(repository.findAll()).thenReturn(tasks);

        //When
        List<Task> retrievedTasks = service.getAllTasks();

        //Then
        assertEquals(2, retrievedTasks.size());
        assertEquals(ID_1, retrievedTasks.get(0).getId());
        assertEquals(TITLE_1, retrievedTasks.get(0).getTitle());
        assertEquals(CONTENT_1, retrievedTasks.get(0).getContent());
        assertEquals(ID_2, retrievedTasks.get(1).getId());
        assertEquals(TITLE_2, retrievedTasks.get(1).getTitle());
        assertEquals(CONTENT_2, retrievedTasks.get(1).getContent());

    }

    @Test
    public void testGetTask() {
        //Given
        Task task = new Task(ID_1, TITLE_1, CONTENT_1);

        when(repository.findById(ID_1)).thenReturn(ofNullable(task));

        //When
        Optional<Task> retrievedTask = service.getTask(ID_1);

        //Then
        assertTrue(retrievedTask.isPresent());
    }

    @Test
    public void testSaveTask() {
        //Given
        Task task = new Task(ID_2, TITLE_2, CONTENT_2);

        when(repository.save(task)).thenReturn(task);

        //When
        Task savedTask = service.saveTask(task);

        //Then
        assertEquals(ID_2, savedTask.getId());
        assertEquals(TITLE_2, savedTask.getTitle());
        assertEquals(CONTENT_2, savedTask.getContent());
    }

    @Test
    public void testDeleteTask() {
        //Given
        //When
        service.deleteTask(1L);

        //Then
        verify(repository, times(1)).deleteById(1L);
    }
}