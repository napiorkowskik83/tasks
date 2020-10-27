package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskMapperTestSuite {
    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final String TITLE_1 = "test title no. 1";
    private static final String TITLE_2 = "test title no. 2";
    private static final String CONTENT_1 = "test content no. 1";
    private static final String CONTENT_2 = "test content no. 2";

    @Autowired
    TaskMapper taskMapper;

    @Test
    public void testMapToTask() {
        //Given
        TaskDto taskDto = new TaskDto(ID_1, TITLE_1, CONTENT_1);
        //When
        Task task = taskMapper.mapToTask(taskDto);
        //Then
        assertEquals(ID_1, task.getId());
        assertEquals(TITLE_1, task.getTitle());
        assertEquals(CONTENT_1, task.getContent());
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        Task task = new Task(ID_1, TITLE_1, CONTENT_1);
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then
        assertEquals(ID_1, taskDto.getId());
        assertEquals(TITLE_1, taskDto.getTitle());
        assertEquals(CONTENT_1, taskDto.getContent());
    }

    @Test
    public void TestMapToTaskDtoList() {
        //Given
        Task task1 = new Task(ID_1, TITLE_1, CONTENT_1);
        Task task2 = new Task(ID_2, TITLE_2, CONTENT_2);
        List<Task> taskList = new ArrayList<>();
        taskList.add(task1);
        taskList.add(task2);
        //When
        List<TaskDto> taskDtoList = taskMapper.mapToTaskDtoList(taskList);
        //Then
        assertEquals(2, taskDtoList.size());
        assertEquals(ID_1, taskDtoList.get(0).getId());
        assertEquals(TITLE_1, taskDtoList.get(0).getTitle());
        assertEquals(CONTENT_1, taskDtoList.get(0).getContent());
        assertEquals(ID_2, taskDtoList.get(1).getId());
        assertEquals(TITLE_2, taskDtoList.get(1).getTitle());
        assertEquals(CONTENT_2, taskDtoList.get(1).getContent());
    }
}