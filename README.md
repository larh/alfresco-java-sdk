# Alfresco APS Java SDK

SDK Java para consumir la API REST de Alfresco Process Services (APS) usando Java 17 y Maven.

## Objetivo

Este proyecto sirve como base para construir clientes Java reutilizables para APS, con soporte para:

- autenticación
- procesos
- tareas
- usuarios
- grupos y permisos
- consultas REST a APS

## Requisitos

- Java 17+
- Maven 3.9+
- Un entorno APS con acceso a la API REST

## Estructura del proyecto

- `src/main/java/com/alfresco/aps/sdk` - código principal del SDK
- `src/test/java` - pruebas unitarias

## Configuración mínima

```java
AlfrescoApsConfig config = new AlfrescoApsConfig(
    "https://aps.example.com",
    "admin",
    "admin"
);

AlfrescoApsClient client = new AlfrescoApsClient(config, new BasicAuthenticationProvider(config.getUsername(), config.getPassword()));

ProcessApi processApi = client.processApi();
```

## Ejemplo de uso

```java
var processApi = client.processApi();
var definitions = processApi.getProcessDefinitions();
System.out.println(definitions);
```

## Nota

APS usa la API REST de Activiti/Alfresco Process Services. Este SDK está pensado como una librería ligera, sin depender de Spring Boot.

## Siguiente paso sugerido

- implementar DTOs reales de APS
- agregar caché de autenticación
- añadir soporte para OAuth2
- encapsular endpoints de tareas, procesos y usuarios
