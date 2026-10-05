package com.alfresco.aps.sdk;

import com.alfresco.aps.sdk.model.ProcessDefinition;
import com.alfresco.aps.sdk.model.ProcessInstance;
import com.alfresco.aps.sdk.model.Task;
import com.alfresco.aps.sdk.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AlfrescoApsClientTest {

    private AlfrescoApsClient client;

    @BeforeEach
    public void setUp() {
        // Configura con los datos de tu entorno APS
        String baseUrl = "http://localhost:8080";
        String username = "admin";
        String password = "admin";
        client = new AlfrescoApsClient(baseUrl, username, password);
    }

    @Test
    public void testClientInitialization() {
        assertNotNull(client);
        assertNotNull(client.processApi());
        assertNotNull(client.taskApi());
        assertNotNull(client.userApi());
        assertNotNull(client.groupApi());
    }

    @Test
    public void testGetProcessDefinitions() {
        List<ProcessDefinition> definitions = client.processApi().getProcessDefinitions();
        assertNotNull(definitions);
    }

    @Test
    public void testGetTasks() {
        List<Task> tasks = client.taskApi().getTasks();
        assertNotNull(tasks);
    }

    @Test
    public void testGetUsers() {
        List<User> users = client.userApi().getUsers();
        assertNotNull(users);
    }

    @Test
    public void testStartProcess() {
        Map<String, Object> variables = new HashMap<>();
        variables.put("requester", "admin");
        ProcessInstance instance = client.processApi().startProcess("vacationRequest", variables);
        assertNotNull(instance);
    }
}
