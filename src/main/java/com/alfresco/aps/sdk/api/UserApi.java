package com.alfresco.aps.sdk.api;

import com.alfresco.aps.sdk.client.ApiClient;
import com.alfresco.aps.sdk.model.User;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class UserApi {
    private final ApiClient apiClient;
    private final ObjectMapper objectMapper;

    public UserApi(ApiClient apiClient) {
        this.apiClient = apiClient;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Obtiene todos los usuarios disponibles
     */
    public List<User> getUsers() {
        JsonNode response = apiClient.get("/activiti-app/api/enterprise/users");
        List<User> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            for (JsonNode node : response) {
                User user = objectMapper.convertValue(node, User.class);
                result.add(user);
            }
        }
        return result;
    }

    /**
     * Obtiene un usuario por ID
     */
    public User getUser(String userId) {
        String path = "/activiti-app/api/enterprise/users/" + userId;
        JsonNode response = apiClient.get(path);
        return objectMapper.convertValue(response, User.class);
    }

    /**
     * Obtiene el usuario actual (autenticado)
     */
    public User getCurrentUser() {
        JsonNode response = apiClient.get("/activiti-app/api/enterprise/profile");
        return objectMapper.convertValue(response, User.class);
    }

    /**
     * Busca usuarios por nombre o email
     */
    public List<User> searchUsers(String filter) {
        String path = "/activiti-app/api/enterprise/users?filter=" + filter;
        JsonNode response = apiClient.get(path);
        List<User> result = new ArrayList<>();
        if (response != null && response.isArray()) {
            for (JsonNode node : response) {
                User user = objectMapper.convertValue(node, User.class);
                result.add(user);
            }
        }
        return result;
    }
}
