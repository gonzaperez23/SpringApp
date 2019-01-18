package com.app.springboot.services;

import com.app.springboot.datatransfer.TaskData;
import com.app.springboot.datatransfer.TaskListResponse;
import com.app.springboot.domain.Task;
import com.app.springboot.domain.repositories.TaskRepository;
import com.app.springboot.exceptions.NotFoundException;
import com.app.springboot.services.providers.LoginUserService;
import com.app.springboot.utils.TaskUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.data.domain.PageRequest;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    TaskService service;

    @Mock
    TaskRepository mockTaskRepository;

    @Mock
    LoginUserService mockLoginUserService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        service = new TaskService();
        service.tasksRepository = mockTaskRepository;
        service.loginUserService = mockLoginUserService;
    }

    @Test
    public void getTasksSuccessfully() throws Exception {
        Mockito.when(mockTaskRepository.findAll(any(PageRequest.class)))
                .thenReturn(TaskUtils.getTaskListResponse());

        TaskListResponse taskdata = service.getTasks(0,1);

        assertEquals("Error cantidad de elementos", 1, taskdata.getResults().size());

        verify(mockTaskRepository, times(0)).findOne(anyInt());
        verify(mockTaskRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void getTasksSuccessfullyEmptyResult() throws Exception {
        Mockito.when(mockTaskRepository.findAll(any(PageRequest.class)))
                .thenReturn(TaskUtils.getTaskListEmptyResponse());

        TaskListResponse taskdata = service.getTasks(0,1);

        assertEquals("Error cantidad de elementos", 0, taskdata.getResults().size());

        verify(mockTaskRepository, times(0)).findOne(anyInt());
        verify(mockTaskRepository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    public void getTasksSuccessfullySearchingUser() throws Exception {
        Mockito.when(mockTaskRepository.findAll(any(PageRequest.class)))
                .thenReturn(TaskUtils.getTaskListResponse());

        TaskListResponse taskdata = service.getTasks(0,1);

        assertEquals("Error cantidad de elementos", 1, taskdata.getResults().size());

        verify(mockTaskRepository, times(0)).findOne(anyInt());
        verify(mockLoginUserService, times(1)).getUserData(anyInt());
    }

    @Test
    public void getTaskSuccessfullyNotFoundUser() throws Exception {
        Mockito.when(mockTaskRepository.findAll(any(PageRequest.class)))
                .thenReturn(TaskUtils.getTaskListResponse());

        TaskListResponse taskdata = service.getTasks(0,1);

        assertEquals("Error cantidad de elementos", 1, taskdata.getResults().size());

        verify(mockTaskRepository, times(0)).findOne(anyInt());
        verify(mockLoginUserService, times(1)).getUserData(anyInt());
    }

    @Test
    public void getTaskSuccessfully() throws Exception {
        Mockito.when(mockTaskRepository.findOne(anyInt()))
                .thenReturn(TaskUtils.getTask());

        TaskData taskdata = service.getTask(1);

        assertEquals("Error validando el nombre de la tarea", "Tarea", taskdata.getName());
        assertEquals("Error validando la descripcion de la tarea", "DescripcionTarea", taskdata.getDescription());

        verify(mockTaskRepository, times(0)).save(any(Task.class));
        verify(mockTaskRepository, times(1)).findOne(anyInt());
    }

    @Test
    public void getTaskSuccessfullyNotFound() throws Exception {
        when(mockTaskRepository.findOne(anyInt()))
                .thenReturn(null);

        try {
            TaskData taskdata = service.getTask(10);
        }
        catch (NotFoundException ex) {
            assertEquals("Error code", 404, ex.getResult().getStatus());
            assertEquals("Error message", "No se encontró la tarea solicitada", ex.getResult().getMessage());
        }

        verify(mockTaskRepository, times(1)).findOne(anyInt());
    }

    @Test
    public void saveNewTaskSuccefully() throws Exception {
        when(mockTaskRepository.save(any(Task.class)))
                .thenReturn(TaskUtils.getTask());

        TaskData taskData = service.createTask(TaskUtils.getTaskDataWithoutId());

        assertEquals("Creación de una nueva tarea", 1, taskData.getId());
        assertEquals("Creación de una nueva tarea", "Tarea", taskData.getName());
    }

    @Test
    public void saveEditTaskSuccefully() throws Exception {
        when(mockTaskRepository.save(any(Task.class)))
                .thenReturn(null);

        try {
            TaskData taskdata = service.editTask(TaskUtils.getTaskData());
        }
        catch (NotFoundException ex) {
            assertEquals("Error code", 404, ex.getResult().getStatus());
        }
    }

    @Test
    public void deleteTaskSuccefully() throws Exception {
        when(mockTaskRepository.findOne(anyInt()))
                .thenReturn(TaskUtils.getTask());

        Boolean boolValue  = service.deleteTask(1);

        assertEquals("Eliminacion de una tarea", true, boolValue);

        verify(mockTaskRepository, times(1)).findOne(anyInt());
        verify(mockTaskRepository, times(1)).delete(anyInt());
    }

    @Test
    public void deleteTaskNotSuccefullyNotFoundTask() throws Exception {
        when(mockTaskRepository.findOne(anyInt()))
                .thenReturn(null);

        try {
            Boolean boolValue = service.deleteTask(1);
        } catch (NotFoundException ex) {
            assertEquals("Error code", 404, ex.getResult().getStatus());
        }
    }
}
