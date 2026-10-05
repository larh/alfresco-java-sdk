package com.alfresco.aps.sdk.api;

import com.alfresco.aps.sdk.client.ApiClient;
import com.alfresco.aps.sdk.model.Group;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class GroupApi {
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;

    public GroupApi(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Obtiene todos los grupos disponibles
     */
    public List<Group> getGroups() {
        JsonNode response = apiClient.get("/activiti-app/api/enterprise/groups");
        List<Group> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            for (JsonNode node : response) {
                Group group = objectMapper.convertValue(node, Group.class);
                result.add(group);
            }
        }
        return result;
    }

    /**
     * Obtiene un grupo por ID
     */
    public Group getGroup(String groupId) {
        String path = "/activiti-app/api/enterprise/groups/" + groupId;
        JsonNode response = apiClient.get(path);
        return objectMapper.convertValue(response, Group.class);
    }

    /**
     * Busca grupos por nombre
     */
    public List<Group> searchGroups(String name) {
        String path = "/activiti-app/api/enterprise/groups?name=" + name;
        JsonNode response = apiClient.get(path);
        List<Group> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            for (JsonNode node : response) {
                Group group = objectMapper.convertValue(node, Group.class);
                result.add(group);
            }
        }
        return result;
    }
}
