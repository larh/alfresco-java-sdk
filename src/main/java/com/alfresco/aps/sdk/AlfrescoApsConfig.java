package com.alfresco.aps.sdk;

public class AlfrescoApsConfig {
    private final String baseUrl;
    private final String username;
    private final String password;

    public AlfrescoApsConfig(String baseUrl, String username, String password) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("baseUrl no puede ser nulo o vacío");
        }
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.username = username;
        this.password = password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
