package com.alfresco.aps.sdk;

public class AlfrescoApsException extends RuntimeException {
    public AlfrescoApsException(String message) {
        super(message);
    }

    public AlfrescoApsException(String message, Throwable cause) {
        super(message, cause);
    }
}
