package com.alfresco.aps.sdk.api;

import com.alfresco.aps.sdk.client.ApiClient;
import com.alfresco.aps.sdk.model.Task;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskApi {
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;

    public TaskApi(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Obtiene todas las tareas disponibles
     */
    public List<Task> getTasks() {
        JsonNode response = apiClient.get("/activiti-app/api/enterprise/tasks");
        List<Task> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            for (JsonNode node : response) {
                Task task = objectMapper.convertValue(node, Task.class);
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Obtiene una tarea por ID
     */
    public Task getTask(String taskId) {
        String path = "/activiti-app/api/enterprise/tasks/" + taskId;
        JsonNode response = apiClient.get(path);
        return objectMapper.convertValue(response, Task.class);
    }

    /**
     * Obtiene las tareas asignadas a un usuario específico
     */
    public List<Task> getTasksByAssignee(String assignee) {
        String path = "/activiti-app/api/enterprise/tasks?assignee=" + assignee;
        JsonNode response = apiClient.get(path);
        List<Task> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            for (JsonNode node : response) {
                Task task = objectMapper.convertValue(node, Task.class);
                result.add(task);
            }
        }
        return result;
    }

    /**
     * Asigna una tarea a un usuario
     */
    public Task assignTask(String taskId, String assigneeId) {
        String path = "/activiti-app/api/enterprise/tasks/" + taskId;
        Map<String, Object> body = new HashMap<>();
        body.put("assignee", assigneeId);
        JsonNode response = apiClient.post(path, body);
        return objectMapper.convertValue(response, Task.class);
    }

    /**
     * Reclama una tarea para el usuario actual
     */
    public Task claimTask(String taskId) {
        String path = "/activiti-app/api/enterprise/tasks/" + taskId + "/claim";
        JsonNode response = apiClient.post(path, new HashMap<>());
        return objectMapper.convertValue(response, Task.class);
    }

    /**
     * Completa una tarea con variables opcionales
     */
    public void completeTask(String taskId, Map<String, Object> variables) {
        String path = "/activiti-app/api/enterprise/tasks/" + taskId + "/complete";
        Map<String, Object> body = new HashMap<>();
        if (variables != null && !variables.isEmpty()) {
            body.put("variables", variables);
        }
        apiClient.post(path, body);
    }

    /**
     * Actualiza una tarea
     */
    public Task updateTask(String taskId, Task taskUpdate) {
        String path = "/activiti-app/api/enterprise/tasks/" + taskId;
        Map<String, Object> body = objectMapper.convertValue(taskUpdate, Map.class);
        JsonNode response = apiClient.post(path, body);
        return objectMapper.convertValue(response, Task.class);
    }
}
