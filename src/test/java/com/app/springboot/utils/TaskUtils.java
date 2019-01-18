package com.app.springboot.utils;

import com.app.springboot.datatransfer.TaskData;
import com.app.springboot.datatransfer.TaskListResponse;
import com.app.springboot.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskUtils {
    public static TaskData getTaskData() {
        TaskData taskData = new TaskData();

        taskData.setId(1);
        taskData.setUser(UserUtils.getUserData());
        taskData.setCreationDate(new Date());
        taskData.setDescription("DescripcionTarea");
        taskData.setName("Tarea");
        taskData.setUserId(1);

        return taskData;
    }

    public static TaskData getTaskDataForPut() {
        TaskData taskData = new TaskData();

        taskData.setId(1);
        taskData.setUser(null);
        taskData.setCreationDate(null);
        taskData.setDescription("DescripcionTarea");
        taskData.setName("Tarea");
        taskData.setUserId(1);

        return taskData;
    }

    public static Task getTask() {
        Task task = new Task();

        task.setId(1);
        task.setCreationDate(null);
        task.setDescription("DescripcionTarea");
        task.setName("Tarea");
        task.setUserId(1);

        return task;
    }

    public static TaskData getTaskDataWithoutId() {
        TaskData taskData = new TaskData();

        taskData.setId(0);
        taskData.setCreationDate(null);
        taskData.setDescription("DescripcionTarea");
        taskData.setName("Tarea");
        taskData.setUserId(1);
        taskData.setUser(null);

        return taskData;
    }

    public static TaskData getTaskDataIncomplete() {
        TaskData taskData = new TaskData();

        taskData.setId(0);
        taskData.setCreationDate(null);
        taskData.setDescription(null);
        taskData.setName(null);
        taskData.setUserId(1);
        taskData.setUser(null);

        return taskData;
    }

    public static TaskListResponse getTaskDataListEmptyResponse() {
        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.setResults(new ArrayList<TaskData>());

        return taskListResponse;
    }

    public static TaskListResponse getTaskDataListResponse() {
        TaskListResponse taskListResponse = new TaskListResponse();
        taskListResponse.setResults(getTasksData());

        return taskListResponse;
    }

    public static List<TaskData> getTasksData() {
        TaskData taskData = TaskUtils.getTaskData();

        List<TaskData> taskDataList = new ArrayList<>();
        taskDataList.add(taskData);

        return taskDataList;
    }

    public static Page<Task> getTaskListEmptyResponse() {
        List<Task> taskListResponse = new ArrayList<>();

        Page<Task> pageTask = new PageImpl<>(taskListResponse);

        return pageTask;
    }

    public static Page<Task> getTaskListResponse() {
        List<Task> taskListResponse;
        taskListResponse = getTasks();

        Page<Task> pageTask = new PageImpl<>(taskListResponse);

        return pageTask;
    }

    public static List<Task> getTasks() {
        Task task = TaskUtils.getTask();

        List<Task> taskDataList = new ArrayList<>();
        taskDataList.add(task);

        return taskDataList;
    }
}
