package com.alfresco.aps.sdk.auth;

import java.net.http.HttpRequest;

public interface AuthenticationProvider {
    void apply(HttpRequest.Builder requestBuilder);
}
