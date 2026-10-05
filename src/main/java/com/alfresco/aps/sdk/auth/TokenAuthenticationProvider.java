package com.alfresco.aps.sdk.auth;

import java.net.http.HttpRequest;

public class TokenAuthenticationProvider implements AuthenticationProvider {
    private final String token;

    public TokenAuthenticationProvider(String token) {
        this.token = token;
    }

    @Override
    public void apply(HttpRequest.Builder requestBuilder) {
        requestBuilder.header("Authorization", "Bearer " + token);
    }
}
