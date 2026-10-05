package com.alfresco.aps.sdk;

import com.alfresco.aps.sdk.api.ProcessApi;
import com.alfresco.aps.sdk.api.TaskApi;
import com.alfresco.aps.sdk.auth.AuthenticationProvider;
import com.alfresco.aps.sdk.auth.BasicAuthenticationProvider;
import com.alfresco.aps.sdk.client.ApiClient;

public class AlfrescoApsClient {
    private final AlfrescoApsConfig config;
    private final ApiClient apiClient;

    public AlfrescoApsClient(AlfrescoApsConfig config, AuthenticationProvider authenticationProvider) {
        this.config = config;
        this.apiClient = new ApiClient(config.getBaseUrl(), authenticationProvider);
    }

    public AlfrescoApsClient(String baseUrl, String username, String password) {
        this(new AlfrescoApsConfig(baseUrl, username, password), new BasicAuthenticationProvider(username, password));
    }

    public ProcessApi processApi() {
        return new ProcessApi(apiClient);
    }

    public TaskApi taskApi() {
        return new TaskApi(apiClient);
    }

    public ApiClient apiClient() {
        return apiClient;
    }

    public AlfrescoApsConfig config() {
        return config;
    }
}
