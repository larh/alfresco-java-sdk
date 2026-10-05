package com.alfresco.aps.sdk;

import com.alfresco.aps.sdk.api.GroupApi;
import com.alfresco.aps.sdk.api.ProcessApi;
import com.alfresco.aps.sdk.api.TaskApi;
import com.alfresco.aps.sdk.api.UserApi;
import com.alfresco.aps.sdk.auth.AuthenticationProvider;
import com.alfresco.aps.sdk.auth.BasicAuthenticationProvider;
import com.alfresco.aps.sdk.client.ApiClient;

public class AlfrescoApsClient {
    private final AlfrescoApsConfig config;
    private final ApiClient apiClient;
    private final ProcessApi processApi;
    private final TaskApi taskApi;
    private final UserApi userApi;
    private final GroupApi groupApi;

    public AlfrescoApsClient(AlfrescoApsConfig config, AuthenticationProvider authenticationProvider) {
        this.config = config;
        this.apiClient = new ApiClient(config.getBaseUrl(), authenticationProvider);
        this.processApi = new ProcessApi(apiClient);
        this.taskApi = new TaskApi(apiClient);
        this.userApi = new UserApi(apiClient);
        this.groupApi = new GroupApi(apiClient);
    }

    /**
     * Crea un cliente APS con autenticación básica (usuario/contraseña)
     */
    public AlfrescoApsClient(String baseUrl, String username, String password) {
        this(new AlfrescoApsConfig(baseUrl, username, password), new BasicAuthenticationProvider(username, password));
    }

    public ProcessApi processApi() {
        return processApi;
    }

    public TaskApi taskApi() {
        return taskApi;
    }

    public UserApi userApi() {
        return userApi;
    }

    public GroupApi groupApi() {
        return groupApi;
    }

    public ApiClient apiClient() {
        return apiClient;
    }

    public AlfrescoApsConfig config() {
        return config;
    }
}
