# Alfresco APS Java SDK

SDK Java 17 para consumir la API REST de Alfresco Process Services (APS) usando Maven.

## Características

- ✅ Cliente HTTP nativo (Java 17 HttpClient)
- ✅ Autenticación Basic Auth y Token
- ✅ API completa para procesos, tareas, usuarios y grupos
- ✅ Modelos DTO con Jackson
- ✅ Manejo de excepciones
- ✅ Sin dependencias externas pesadas
- ✅ Fácil de integrar en cualquier aplicación Java

## Requisitos

- Java 17+
- Maven 3.9+
- Acceso a un servidor Alfresco Process Services (APS)

## Instalación

### Clonar el repositorio

```bash
git clone https://github.com/larh/alfresco-java-sdk.git
cd alfresco-java-sdk
```

### Compilar

```bash
mvn clean package
```

## Uso

### Inicializar el cliente

```java
import com.alfresco.aps.sdk.AlfrescoApsClient;

// Crear cliente con autenticación básica
AlfrescoApsClient client = new AlfrescoApsClient(
    "http://localhost:8080",
    "admin",
    "admin"
);
```

### Procesos

```java
import com.alfresco.aps.sdk.model.ProcessDefinition;
import com.alfresco.aps.sdk.model.ProcessInstance;

// Obtener definiciones de procesos
List<ProcessDefinition> definitions = client.processApi().getProcessDefinitions();

// Iniciar un nuevo proceso
Map<String, Object> variables = new HashMap<>();
variables.put("requester", "john.doe");
variables.put("amount", 5000);

ProcessInstance instance = client.processApi().startProcess(
    "vacationRequest",
    variables
);

System.out.println("Proceso iniciado: " + instance.getId());

// Obtener instancia de proceso
ProcessInstance pi = client.processApi().getProcessInstance(instance.getId());

// Obtener procesos del usuario actual
List<ProcessInstance> myProcesses = client.processApi().getMyProcessInstances("userId");
```

### Tareas

```java
import com.alfresco.aps.sdk.model.Task;

// Obtener todas las tareas
List<Task> tasks = client.taskApi().getTasks();

// Obtener tareas de un usuario
List<Task> assignedTasks = client.taskApi().getTasksByAssignee("john.doe");

// Obtener una tarea específica
Task task = client.taskApi().getTask("taskId123");

// Asignar tarea a un usuario
Task assigned = client.taskApi().assignTask("taskId123", "jane.smith");

// Reclamar una tarea
Task claimed = client.taskApi().claimTask("taskId123");

// Completar una tarea
Map<String, Object> taskVariables = new HashMap<>();
taskVariables.put("approved", true);
taskVariables.put("comments", "Aprobado por gerente");

client.taskApi().completeTask("taskId123", taskVariables);
```

### Usuarios

```java
import com.alfresco.aps.sdk.model.User;

// Obtener todos los usuarios
List<User> users = client.userApi().getUsers();

// Obtener usuario actual
User currentUser = client.userApi().getCurrentUser();
System.out.println("Usuario: " + currentUser.getUsername());

// Obtener usuario por ID
User user = client.userApi().getUser("userId123");

// Buscar usuarios
List<User> results = client.userApi().searchUsers("john");
```

### Grupos

```java
import com.alfresco.aps.sdk.model.Group;

// Obtener todos los grupos
List<Group> groups = client.groupApi().getGroups();

// Obtener grupo por ID
Group group = client.groupApi().getGroup("groupId123");

// Buscar grupos
List<Group> results = client.groupApi().searchGroups("Marketing");
```

## Modelos de datos

### ProcessDefinition
```java
public class ProcessDefinition {
    private String id;                  // ID único
    private String name;                // Nombre del proceso
    private String key;                 // Clave única del proceso
    private String description;         // Descripción
    private Integer version;            // Versión
    private String deploymentId;        // ID de deployment
}
```

### ProcessInstance
```java
public class ProcessInstance {
    private String id;                      // ID único
    private String name;                    // Nombre de la instancia
    private String processDefinitionId;     // ID de la definición
    private String processDefinitionKey;    // Clave del proceso
    private String startedBy;               // Usuario que inició
    private Date startDate;                 // Fecha de inicio
    private Date endDate;                   // Fecha de fin
    private Boolean isEnded;                // ¿Proceso terminado?
    private Map<String, Object> variables;  // Variables del proceso
}
```

### Task
```java
public class Task {
    private String id;                      // ID único
    private String name;                    // Nombre
    private String description;             // Descripción
    private String assignee;                // Asignado a
    private String owner;                   // Propietario
    private Integer priority;               // Prioridad (1-3)
    private String processInstanceId;       // ID instancia proceso
    private String processDefinitionId;     // ID definición proceso
    private Date createDate;                // Fecha de creación
    private Date dueDate;                   // Fecha vencimiento
    private Date endDate;                   // Fecha de fin
    private Map<String, Object> variables;  // Variables de tarea
}
```

### User
```java
public class User {
    private String id;           // ID único
    private String email;        // Email
    private String firstName;    // Nombre
    private String lastName;     // Apellido
    private String username;     // Usuario
    private String status;       // Estado
}
```

### Group
```java
public class Group {
    private String id;       // ID único
    private String name;     // Nombre del grupo
    private String status;   // Estado
}
```

## Manejo de errores

```java
import com.alfresco.aps.sdk.AlfrescoApsException;

try {
    ProcessInstance instance = client.processApi().startProcess("myProcess", new HashMap<>());
} catch (AlfrescoApsException e) {
    System.err.println("Error en APS: " + e.getMessage());
    e.printStackTrace();
}
```

## Configuración avanzada

### Con autenticación por token

```java
import com.alfresco.aps.sdk.auth.TokenAuthenticationProvider;

AlfrescoApsConfig config = new AlfrescoApsConfig(
    "http://localhost:8080",
    "admin",  // usuario (no se usa con token)
    ""        // password (no se usa con token)
);

AlfrescoApsClient client = new AlfrescoApsClient(
    config,
    new TokenAuthenticationProvider("your-jwt-token")
);
```

### Con cliente HTTP personalizado

```java
import com.alfresco.aps.sdk.client.ApiClient;

ApiClient apiClient = new ApiClient(
    "http://localhost:8080",
    new BasicAuthenticationProvider("admin", "admin")
);

// Usar directamente para requests específicas
JsonNode response = apiClient.get("/activiti-app/api/enterprise/profile");
```

## Estructura del proyecto

```
alfresco-java-sdk/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/alfresco/aps/sdk/
    │   ├── AlfrescoApsClient.java          # Cliente principal
    │   ├── AlfrescoApsConfig.java          # Configuración
    │   ├── AlfrescoApsException.java       # Excepción personalizada
    │   ├── api/
    │   │   ├── ProcessApi.java             # API de procesos
    │   │   ├── TaskApi.java                # API de tareas
    │   │   ├── UserApi.java                # API de usuarios
    │   │   └── GroupApi.java               # API de grupos
    │   ├── auth/
    │   │   ├── AuthenticationProvider.java # Interfaz autenticación
    │   │   ├── BasicAuthenticationProvider.java
    │   │   └── TokenAuthenticationProvider.java
    │   ├── client/
    │   │   └── ApiClient.java              # Cliente HTTP
    │   └── model/
    │       ├── ProcessDefinition.java
    │       ├── ProcessInstance.java
    │       ├── Task.java
    │       ├── User.java
    │       ├── Group.java
    │       └── ApiResponse.java
    └── test/java/
        └── com/alfresco/aps/sdk/
            └── AlfrescoApsClientTest.java
```

## Ejecución de tests

```bash
# Ejecutar todos los tests
mvn test

# Ejecutar un test específico
mvn test -Dtest=AlfrescoApsClientTest
```

## Publicación en Maven Central

Para publicar este SDK en Maven Central, sigue estos pasos:

1. Configurar `pom.xml` con información de SCM y distribución
2. Crear cuenta en Sonatype JIRA
3. Firmar el JAR con GPG
4. Ejecutar: `mvn deploy`

## Contribuciones

Las contribuciones son bienvenidas. Por favor:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/amazing-feature`)
3. Commit tus cambios (`git commit -m 'Add amazing feature'`)
4. Push a la rama (`git push origin feature/amazing-feature`)
5. Abre un Pull Request

## Licencia

Apache License 2.0 - ver archivo LICENSE

## Soporte

Para reportar issues o sugerencias:

- Abre un GitHub Issue
- Documentación oficial de Alfresco Process Services: https://docs.alfresco.com/aps/

## Roadmap

- [ ] Soporte para OAuth2
- [ ] Caché de autenticación con TTL
- [ ] Paginación automática de resultados
- [ ] Descargar archivos adjuntos de tareas
- [ ] WebSocket para notificaciones en tiempo real
- [ ] Publicación en Maven Central
- [ ] Javadoc generado
