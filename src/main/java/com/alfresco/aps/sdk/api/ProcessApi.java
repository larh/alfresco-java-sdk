package com.alfresco.aps.sdk.api;

import com.alfresco.aps.sdk.client.ApiClient;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProcessApi {
    private final ApiClient apiClient;

    public ProcessApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public List<JsonNode> getProcessDefinitions() {
        JsonNode response = apiClient.get("/activiti-app/api/enterprise/process-definitions");
        List<JsonNode> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            response.forEach(result::add);
        }
        return result;
    }

    public JsonNode startProcess(String processDefinitionKey, Map<String, Object> variables) {
        String path = "/activiti-app/api/process-instances";
        Map<String, Object> body = Map.of(
                "processDefinitionKey", processDefinitionKey,
                "variables", variables
        );
        return apiClient.post(path, body);
    }
}
