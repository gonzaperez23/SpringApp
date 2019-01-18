package com.app.springboot.services;

import com.app.springboot.datatransfer.TaskData;
import com.app.springboot.datatransfer.TaskListResponse;
import com.app.springboot.datatransfer.UserData;
import com.app.springboot.domain.Task;
import com.app.springboot.domain.repositories.TaskRepository;
import com.app.springboot.exceptions.NotFoundException;
import com.app.springboot.services.providers.LoginUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    TaskRepository tasksRepository;

    @Autowired
    LoginUserService loginUserService;

    public TaskListResponse getTasks(Integer offset, Integer limit) throws Exception {
        Page<Task> result;
        Pageable pag = new PageRequest(offset, limit);
        List<TaskData> taskDataResult = new ArrayList<>();

        result = tasksRepository.findAll(pag);
        if (result != null) {
            for (Task task : result) {
                UserData userData = searchUserData(task.getUserId());
                taskDataResult.add(mapTaskToTaskData(task, userData));
            }
        }

        TaskListResponse taskList = new TaskListResponse();
        taskList.setResults(taskDataResult);

        return taskList;
    }

    public TaskData getTask(Integer taskId) throws Exception {
        Task taskResult = tasksRepository.findOne(taskId);

        if (taskResult == null) {
            NotFoundException ex = new NotFoundException("No se encontró la tarea solicitada");
            throw ex;
        }

        UserData userData = searchUserData(taskResult.getUserId());

        return mapTaskToTaskData(taskResult, userData);
    }

    public TaskData createTask(TaskData taskData) throws Exception {
        try {
            Task task = mapTaskDataToTask(taskData);

            saveTask(task);

            taskData.setId((int)tasksRepository.count() + 1);
            return taskData;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public TaskData editTask(TaskData taskData) throws Exception {
        try {
            Task task = mapTaskDataToTask(getTask(taskData.getId()));            
            
            task.setDescription(taskData.getDescription());
            task.setName(taskData.getName());
            task.setUserId(taskData.getUser().getId());

            tasksRepository.save(task);

            return taskData;
        } catch (NotFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean deleteTask(int taskId) throws NotFoundException
    {
        Task taskResult = tasksRepository.findOne(taskId);
        if (taskResult == null) {
            NotFoundException ex = new NotFoundException("No se encontró la tarea solicitada");
            throw ex;
        }

        tasksRepository.delete(taskId);

        return true;
    }

    private UserData searchUserData(Integer userId) throws Exception {
        return loginUserService.getUserData(userId);
    }

    private void saveTask(Task task) throws Exception {
        UserData userData = loginUserService.getUserData(task.getUserId());
        tasksRepository.save(task);
    }

    public TaskData mapTaskToTaskData(Task task, UserData userData) {
        TaskData response = new TaskData();

        response.setId(task.getId());
        response.setName(task.getName());
        response.setDescription(task.getDescription());
        response.setUserId(task.getUserId());
        response.setUser(userData);
        response.setCreationDate(task.getCreationDate());

        return response;
    }

    public Task mapTaskDataToTask(TaskData taskData) {
        Task response = new Task();

        response.setId(taskData.getId());
        response.setName(taskData.getName());
        response.setDescription(taskData.getDescription());
        response.setUserId(taskData.getUserId());
        response.setCreationDate(new Date());

        return response;
    }
}
