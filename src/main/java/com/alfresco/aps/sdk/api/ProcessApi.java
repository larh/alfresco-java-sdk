package com.alfresco.aps.sdk.api;

import com.alfresco.aps.sdk.client.ApiClient;
import com.alfresco.aps.sdk.model.ProcessDefinition;
import com.alfresco.aps.sdk.model.ProcessInstance;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessApi {
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;

    public ProcessApi(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Obtiene todas las definiciones de procesos disponibles en APS
     */
    public List<ProcessDefinition> getProcessDefinitions() {
        JsonNode response = apiClient.get("/activiti-app/api/enterprise/process-definitions");
        List<ProcessDefinition> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            for (JsonNode node : response) {
                ProcessDefinition definition = objectMapper.convertValue(node, ProcessDefinition.class);
                result.add(definition);
            }
        }
        return result;
    }

    /**
     * Obtiene una definición de proceso por ID
     */
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        String path = "/activiti-app/api/enterprise/process-definitions/" + processDefinitionId;
        JsonNode response = apiClient.get(path);
        return objectMapper.convertValue(response, ProcessDefinition.class);
    }

    /**
     * Inicia una nueva instancia de un proceso
     */
    public ProcessInstance startProcess(String processDefinitionKey, Map<String, Object> variables) {
        String path = "/activiti-app/api/enterprise/process-instances";
        Map<String, Object> body = new HashMap<>();
        body.put("processDefinitionKey", processDefinitionKey);
        if (variables != null && !variables.isEmpty()) {
            body.put("variables", variables);
        }
        JsonNode response = apiClient.post(path, body);
        return objectMapper.convertValue(response, ProcessInstance.class);
    }

    /**
     * Obtiene todas las instancias de procesos
     */
    public List<ProcessInstance> getProcessInstances() {
        JsonNode response = apiClient.get("/activiti-app/api/enterprise/process-instances");
        List<ProcessInstance> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            for (JsonNode node : response) {
                ProcessInstance instance = objectMapper.convertValue(node, ProcessInstance.class);
                result.add(instance);
            }
        }
        return result;
    }

    /**
     * Obtiene una instancia de proceso por ID
     */
    public ProcessInstance getProcessInstance(String processInstanceId) {
        String path = "/activiti-app/api/enterprise/process-instances/" + processInstanceId;
        JsonNode response = apiClient.get(path);
        return objectMapper.convertValue(response, ProcessInstance.class);
    }

    /**
     * Obtiene los procesos activos de un usuario específico
     */
    public List<ProcessInstance> getMyProcessInstances(String userId) {
        String path = "/activiti-app/api/enterprise/process-instances?userId=" + userId;
        JsonNode response = apiClient.get(path);
        List<ProcessInstance> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            for (JsonNode node : response) {
                ProcessInstance instance = objectMapper.convertValue(node, ProcessInstance.class);
                result.add(instance);
            }
        }
        return result;
    }
}
