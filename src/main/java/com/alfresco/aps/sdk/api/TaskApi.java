package com.alfresco.aps.sdk.api;

import com.alfresco.aps.sdk.client.ApiClient;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskApi {
    private final ApiClient apiClient;

    public TaskApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<JsonNode> getTasks() {
        JsonNode response = apiClient.get("/activiti-app/api/tasks");
        List<JsonNode> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            response.forEach(result::add);
        }
        return result;
    }

    public JsonNode claimTask(String taskId, String userId) {
        String path = "/activiti-app/api/tasks/" + taskId + "/claim";
        return apiClient.post(path, Map.of("assignee", userId));
    }

    public JsonNode completeTask(String taskId, Map<String, Object> variables) {
        String path = "/activiti-app/api/tasks/" + taskId + "/complete";
        return apiClient.post(path, Map.of("variables", variables));
    }
}
