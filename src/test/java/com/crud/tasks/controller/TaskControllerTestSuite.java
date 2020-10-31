package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static java.util.Optional.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {
    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final String TITLE_1 = "test title no. 1";
    private static final String TITLE_2 = "test title no. 2";
    private static final String CONTENT_1 = "test content no. 1";
    private static final String CONTENT_2 = "test content no. 2";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskMapper mapper;

    @MockBean
    private DbService service;

    @Test
    public void testGetTasks() throws Exception {
        //Given
        TaskDto task1 = new TaskDto(ID_1, TITLE_1, CONTENT_1);
        TaskDto task2 = new TaskDto(ID_2, TITLE_2, CONTENT_2);
        List<TaskDto> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        when(service.getAllTasks()).thenReturn(new ArrayList<>());
        when(mapper.mapToTaskDtoList(anyList())).thenReturn(tasks);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(ID_1), Long.class))
                .andExpect(jsonPath("$[0].title", is(TITLE_1)))
                .andExpect(jsonPath("$[0].content", is(CONTENT_1)))
                .andExpect(jsonPath("$[1].id", is(ID_2), Long.class))
                .andExpect(jsonPath("$[1].title", is(TITLE_2)))
                .andExpect(jsonPath("$[1].content", is(CONTENT_2)));
    }

    @Test
    public void testGetTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(ID_1, TITLE_1, CONTENT_1);
        Task task = new Task(ID_1, TITLE_1, CONTENT_1);

        when(service.getTask(ID_1)).thenReturn(of(task));
        when(mapper.mapToTaskDto(task)).thenReturn(taskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask").contentType(MediaType.APPLICATION_JSON)
                .param("taskId", String.valueOf(ID_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID_1), Long.class))
                .andExpect(jsonPath("$.title", is(TITLE_1)))
                .andExpect(jsonPath("$.content", is(CONTENT_1)));
    }

    @Test
    public void testDeleteTask() throws Exception{
        //Given
        //When & Then
        mockMvc.perform(delete("/v1/task/deleteTask").contentType(MediaType.APPLICATION_JSON)
                .param("taskId", String.valueOf(ID_2)))
                .andExpect(status().isOk());

        verify(service,times(1)).deleteTask(ID_2);
    }

    @Test
    public void testUpdateTask() throws Exception {
        //Given
        TaskDto taskDto = new TaskDto(ID_1, TITLE_1, CONTENT_1);
        Task task = new Task(ID_1, TITLE_1, CONTENT_1);

        when(mapper.mapToTask(any(TaskDto.class))).thenReturn(task);
        when(service.saveTask(task)).thenReturn(task);
        when(mapper.mapToTaskDto(task)).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(ID_1), Long.class))
                .andExpect(jsonPath("$.title", is(TITLE_1)))
                .andExpect(jsonPath("$.content", is(CONTENT_1)));
    }

    @Test
    public void testCreateTask() throws Exception{
        //Given
        TaskDto taskDto = new TaskDto(ID_1, TITLE_1, CONTENT_1);
        Task task = new Task(ID_1, TITLE_1, CONTENT_1);

        when(mapper.mapToTask(any(TaskDto.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());

        verify(mapper, times(1)).mapToTask(any(TaskDto.class));
        verify(service,times(1)).saveTask(any(Task.class));
    }
}