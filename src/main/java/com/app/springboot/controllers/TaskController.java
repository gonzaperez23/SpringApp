package com.app.springboot.controllers;

import com.app.springboot.datatransfer.PaginationValues;
import com.app.springboot.datatransfer.TaskData;
import com.app.springboot.datatransfer.TaskListResponse;
import com.app.springboot.exceptions.RequestValidationException;
import com.app.springboot.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/tasks")
public class TaskController extends BaseController {
    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/{offset}/{limit}", method = RequestMethod.GET)
    public HttpEntity<TaskListResponse> searchTask(@PathVariable Optional<Integer> offset,
                                                   @PathVariable Optional<Integer> limit) throws Exception {
        PaginationValues paginationValues = paginationUtility.CompletePaginationValues(offset, limit);

        TaskListResponse response = taskService.getTasks(paginationValues.getOffset(), paginationValues.getLimit());

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public HttpEntity<TaskData> getTask(@PathVariable int id) throws Exception  {
        TaskData response = taskService.getTask(id);

        return ResponseEntity.ok(response);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public HttpEntity<TaskData> createTask(@RequestBody TaskData taskDataRequest) throws Exception {
        if (!validateRequest(taskDataRequest)) {
            String message = "El id de usuario, nombre y descripción son datos requeridos.";
            RequestValidationException ex = new RequestValidationException(message);
            throw ex;
        }

        TaskData taskDataResult = taskService.createTask(taskDataRequest);

        return ResponseEntity.ok(taskDataResult);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    public HttpEntity<TaskData> editTask(@PathVariable int id,
                                         @RequestBody TaskData taskDataRequest) throws Exception {

        if (!validateRequest(taskDataRequest)) {
            String message = "El id de usuario, nombre y descripción son datos requeridos.";
            RequestValidationException ex = new RequestValidationException(message);
            throw ex;
        }

        taskDataRequest.setId(id);
        TaskData taskDataResult = taskService.editTask(taskDataRequest);

        return ResponseEntity.ok(taskDataResult);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public HttpEntity<Boolean> deleteTask(@PathVariable int id) throws Exception {
        Boolean taskDataResult = taskService.deleteTask(id);

        return ResponseEntity.ok(taskDataResult);
    }

    private Boolean validateRequest(TaskData taskDataRequest) {
        if (taskDataRequest.getUserId() == null ||
                taskDataRequest.getName() == null ||
                taskDataRequest.getDescription() == null) {
            return false;
        } else {
            return true;
        }
    }
}
