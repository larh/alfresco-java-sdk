package com.alfresco.aps.sdk.auth;

import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthenticationProvider implements AuthenticationProvider {
    private final String username;
    private final String password;

    public BasicAuthenticationProvider(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void apply(HttpRequest.Builder requestBuilder) {
        String credentials = username + ":" + password;
        String encoded = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
        requestBuilder.header("Authorization", "Basic " + encoded);
    }
}
