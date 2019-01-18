package com.app.springboot.controller;

import com.app.springboot.controllers.TaskController;
import com.app.springboot.datatransfer.TaskData;
import com.app.springboot.datatransfer.TaskListResponse;
import com.app.springboot.exceptions.NotFoundException;
import com.app.springboot.services.TaskService;
import com.app.springboot.utils.TaskUtils;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    TaskService taskService;

    @Autowired
    private Gson jsonParser;

    @Test
    public void getTasksSuccesfully() throws Exception {
        given(this.taskService.getTasks(anyInt(), anyInt()))
                .willReturn(TaskUtils.getTaskDataListResponse());

        mvc.perform(get("/api/tasks/0/5")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("results[0].id").value(1))
                .andExpect(jsonPath("results[0].name").value("Tarea"))
                .andExpect(jsonPath("results[0].description").value("DescripcionTarea"))
                .andExpect(jsonPath("results[0].userId").value(1))
                .andExpect(jsonPath("results[0].user.id").value(1))
                .andExpect(jsonPath("results[0].user.firstName").value("Nombre"))
                .andExpect(jsonPath("results[0].user.city.id").value(1));
    }

    @Test
    public void getTaskSuccefully() throws Exception {
        given(this.taskService.getTask(eq(1)))
                .willReturn(getTaskData());

        mvc.perform(get("/api/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Tarea"))
                .andExpect(jsonPath("description").value("DescripcionTarea"))
                .andExpect(jsonPath("userId").value(1))
                .andExpect(jsonPath("user.id").value(1))
                .andExpect(jsonPath("user.firstName").value("Nombre"))
                .andExpect(jsonPath("user.city.id").value(1));
    }

    @Test
    public void getTasksSuccesfullyEmpty() throws Exception {
        given(this.taskService.getTasks(anyInt(), anyInt()))
                .willReturn(TaskUtils.getTaskDataListEmptyResponse());

        mvc.perform(get("/api/tasks/0/5")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("results.length()").value(0));
    }

    @Test
    public void getTask404NotFoundData() throws Exception {
        given(this.taskService.getTask(eq(10)))
                .willThrow(new NotFoundException("No se encontró la tarea solicitada"));

        mvc.perform(get("/api/tasks/10")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message").value("No se encontró la tarea solicitada"))
                .andExpect(jsonPath("error").value("Not Found"))
                .andExpect(jsonPath("status").value(404));
    }

    @Test
    public void createNewTaskSuccefully() throws Exception {
        when(this.taskService.createTask(any(TaskData.class)))
                .thenReturn(getTaskDataWithoutId());

        String taskValue = jsonParser.toJson(getTaskDataWithoutId());

        mvc.perform(post("/api/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskValue))
                .andExpect(status().isOk());
    }

    @Test
    public void createNewTaskNoSuccefullyInvalidParameters() throws Exception {
        String taskValue = jsonParser.toJson(getTaskDataIncomplete());

        mvc.perform(post("/api/tasks/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskValue))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("message").value("El id de usuario, nombre y descripción son datos requeridos."));
    }

    @Test
    public void editTaskSuccefully() throws Exception {
        when(this.taskService.editTask(any(TaskData.class)))
                .thenReturn(TaskUtils.getTaskDataForPut());

        String taskValue = jsonParser.toJson(TaskUtils.getTaskDataForPut());

        mvc.perform(put("/api/tasks/edit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskValue))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("name").value("Tarea"));
    }

    @Test
    public void editTaskNoSuccefullyInvalidParameters() throws Exception {
        String taskValue = jsonParser.toJson(TaskUtils.getTaskDataIncomplete());

        mvc.perform(put("/api/tasks/edit/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(taskValue))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("message").value("El id de usuario, nombre y descripción son datos requeridos."));
    }

    @Test
    public void deleteTaskSuccefully() throws Exception {
        given(this.taskService.deleteTask(anyInt()))
                .willReturn(true);

        mvc.perform(delete("/api/tasks/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void deleteTaskNotSuccefullyNotFoundException() throws Exception {
        given(this.taskService.deleteTask(anyInt()))
                .willThrow(new NotFoundException("No se encontró la tarea solicitada"));

        mvc.perform(delete("/api/tasks/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("message").value("No se encontró la tarea solicitada"))
                .andExpect(jsonPath("error").value("Not Found"))
                .andExpect(jsonPath("status").value(404));
    }

    public TaskData getTaskDataIncomplete() {
        return TaskUtils.getTaskDataIncomplete();
    }

    public TaskData getTaskDataWithoutId() {
        return TaskUtils.getTaskDataWithoutId();
    }

    public TaskData getTaskData() {
        return TaskUtils.getTaskData();
    }
}
