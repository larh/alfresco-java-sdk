package com.alfresco.aps.sdk.client;

import com.alfresco.aps.sdk.AlfrescoApsException;
import com.alfresco.aps.sdk.auth.AuthenticationProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    private final String baseUrl;
    private final AuthenticationProvider authenticationProvider;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private static final long REQUEST_TIMEOUT_SECONDS = 30;

    public ApiClient(String baseUrl, AuthenticationProvider authenticationProvider) {
        this.baseUrl = baseUrl;
        this.authenticationProvider = authenticationProvider;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(java.time.Duration.ofSeconds(REQUEST_TIMEOUT_SECONDS))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Ejecuta una petición GET
     */
    public JsonNode get(String path) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .timeout(java.time.Duration.ofSeconds(REQUEST_TIMEOUT_SECONDS))
                .header("Accept", "application/json");

        if (authenticationProvider != null) {
            authenticationProvider.apply(requestBuilder);
        }

        HttpRequest request = requestBuilder.GET().build();
        return execute(request);
    }

    /**
     * Ejecuta una petición POST
     */
    public JsonNode post(String path, Map<String, Object> body) {
        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .timeout(java.time.Duration.ofSeconds(REQUEST_TIMEOUT_SECONDS))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json");

            if (authenticationProvider != null) {
                authenticationProvider.apply(requestBuilder);
            }

            HttpRequest request = requestBuilder.POST(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
            return execute(request);
        } catch (JsonProcessingException e) {
            throw new AlfrescoApsException("No se pudo serializar el cuerpo JSON", e);
        }
    }

    /**
     * Ejecuta una petición PUT
     */
    public JsonNode put(String path, Map<String, Object> body) {
        try {
            String jsonBody = objectMapper.writeValueAsString(body);
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + path))
                    .timeout(java.time.Duration.ofSeconds(REQUEST_TIMEOUT_SECONDS))
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json");

            if (authenticationProvider != null) {
                authenticationProvider.apply(requestBuilder);
            }

            HttpRequest request = requestBuilder.PUT(HttpRequest.BodyPublishers.ofString(jsonBody)).build();
            return execute(request);
        } catch (JsonProcessingException e) {
            throw new AlfrescoApsException("No se pudo serializar el cuerpo JSON", e);
        }
    }

    /**
     * Ejecuta una petición DELETE
     */
    public void delete(String path) {
        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .timeout(java.time.Duration.ofSeconds(REQUEST_TIMEOUT_SECONDS))
                .header("Accept", "application/json");

        if (authenticationProvider != null) {
            authenticationProvider.apply(requestBuilder);
        }

        HttpRequest request = requestBuilder.DELETE().build();
        execute(request);
    }

    private JsonNode execute(HttpRequest request) {
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new AlfrescoApsException(
                        "Error al invocar APS: " + response.statusCode() + " - " + response.body()
                );
            }

            if (response.body() == null || response.body().isBlank()) {
                return objectMapper.createObjectNode();
            }

            return objectMapper.readTree(response.body());
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AlfrescoApsException("Fallo al ejecutar la request HTTP", e);
        }
    }
}
