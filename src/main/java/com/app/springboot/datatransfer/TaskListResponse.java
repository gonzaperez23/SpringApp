package com.app.springboot.datatransfer;
import java.util.*;

public class TaskListResponse {
    List<TaskData> results;

    public List<TaskData> getResults() {
        return results;
    }

    public void setResults(List<TaskData> tasks) {
        results = tasks;
    }
}
