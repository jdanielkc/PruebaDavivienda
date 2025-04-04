# Sistema de Gestión de Reservas y Eventos - DaviEventos

## Caso de Negocio (KATA)

Se solicita desarrollar una plataforma de eventos sencilla en donde en la página principal se puedan ver todos los eventos disponibles que pueda tener una persona (conciertos, citas médicas, restaurantes, cine y todos los eventos que usted desee) que permita a los usuarios ver eventos disponibles, registrarse y reservar espacios.

- La persona debe estar registrada: ingresar e iniciar sesión con el método de autenticación que usted desee.
- Debe reservar y mostrar los eventos que tiene reservados. 
- Los eventos deben tener un límite de personas; si supera el número de personas ya no les debe permitir realizar la inscripción y arrojar un mensaje. 
- Pueden utilizar IA y en la sustentación deben explicar cómo lo utilizaron.
- El orden de la sustentación será asignado de acuerdo al orden en el que envíen el link y la hora de la sustentación será confirmada por correo.

## Diseño y Arquitectura Propuesta

![DiagramaEntidadRelacion](https://github.com/user-attachments/assets/ccb2b8e8-6bb5-4cf3-967a-11e6cf6ed87a)

## Descripción del Proyecto Backend

Este proyecto implementa una API RESTful para una plataforma de gestión de eventos que permite a los usuarios ver eventos disponibles, registrarse en la plataforma, iniciar sesión y realizar reservas para diferentes tipos de eventos (conciertos, citas médicas, restaurantes, cine, etc.). El sistema valida la disponibilidad de cupos y mantiene un registro de todas las reservaciones realizadas por los usuarios.

## Tecnologías Implementadas

* **Java 17**: Lenguaje base para el desarrollo del backend
* **Spring Boot 3.4.4**: Framework para el desarrollo de aplicaciones Java
* **Spring Security**: Para la autenticación y autorización
* **AWS RDS (PostgreSQL)**: Se usa AWS para consumir la base de datos
* **Spring Data JPA**: Para la persistencia de datos
* **JWT (JSON Web Tokens)**: Implementación de autenticación stateless
* **PostgreSQL**: Sistema de gestión de base de datos relacional
* **Maven**: Gestión de dependencias y construcción del proyecto
* **Lombok**: Reducción de código boilerplate
* **Logback**: Framework de logging con soporte para formatos personalizados
* **JUnit & Spring Test**: Framework de pruebas unitarias e integración

## Patrones de Diseño Implementados

* **DTO (Data Transfer Object)**: Para transferir datos entre capas.
* **Repository Pattern**: Para abstraer y encapsular el acceso a datos.
* **Service Layer**: Para encapsular la lógica de negocio.
* **Builder Pattern**: Utilizado con Lombok para la creación de objetos.
* **Dependency Injection**: Fundamental en la arquitectura Spring.

## Buenas Prácticas Implementadas

El sistema de gestión de reservas y eventos ha sido desarrollado siguiendo un conjunto integral de buenas prácticas de ingeniería de software, reforzadas con análisis estático de código mediante SpotBugs:

### Arquitectura y Diseño

- **Arquitectura en Capas**: Clara separación entre controladores, servicios, repositorios y entidades
- **Principios SOLID**: Implementación de interfaces, responsabilidad única y dependencias por abstracción
- **Patrones de Diseño**: Builder, DTO, Repository y Service con implementaciones correctamente segregadas
- **Inmutabilidad**: DTOs inmutables para garantizar la integridad de los datos

### Seguridad Reforzada

- **JWT con Codificación Explícita**: Tokens JWT implementados con codificación UTF-8 explícita
- **CSRF Configurado**: Protección CSRF habilitada para endpoints web con exclusiones específicas para APIs
- **Password Hashing**: Almacenamiento seguro de contraseñas mediante BCrypt
- **Validación de Entradas**: Sanitización y validación exhaustiva para prevenir inyecciones
- **Control de Acceso**: Implementación robusta con Spring Security y verificaciones de autorización

### Calidad de Código

- **Análisis Estático con SpotBugs**: Integración de análisis de código para detección temprana de bugs
- **Documentación Javadoc**: Documentación completa y consistente en todas las clases y métodos
- **Logging Seguro**: Patrones de logging que previenen vulnerabilidades CRLF usando placeholders
- **Manejo Eficiente de Referencias Nulas**: Uso adecuado de Optional y validaciones preventivas

### Transaccionalidad y Persistencia

- **Gestión de Transacciones**: Anotaciones `@Transactional` con niveles de aislamiento apropiados
- **JPA/Hibernate**: Mapeo objeto-relacional optimizado mediante Spring Data JPA
- **Paginación**: Implementada para consultas de colecciones grandes
- **Integridad Referencial**: Relaciones entre entidades correctamente definidas

### Configuración y Escalabilidad

- **Configuración Externalizada**: Propiedades en archivos de configuración externos
- **Encoding UTF-8 Explícito**: Configuración consistente de codificación en todo el proyecto
- **API RESTful**: Diseño de endpoints siguiendo estándares REST
- **Perfiles de Entorno**: Configuraciones separadas para desarrollo, pruebas y producción

### Integración Continua y Calidad

- **Análisis Automático**: SpotBugs integrado en el ciclo de construcción mediante Maven
- **Exclusiones Configuradas**: Falsos positivos gestionados mediante archivo de exclusiones específicas
- **Reportes de Calidad**: Generación de informes detallados sobre problemas potenciales
- **Corrección Proactiva**: Resolución sistemática de issues detectados por el análisis estático

### Mejoras Implementadas Tras Análisis Estático

- **Corrección de Vulnerabilidades de Seguridad**: Implementación adecuada de CSRF y codificación explícita

El uso de SpotBugs como herramienta de análisis estático de código ha permitido identificar y corregir proactivamente problemas potenciales antes de que puedan manifestarse en entornos de producción, contribuyendo significativamente a la robustez y seguridad del sistema.

## Estructura del Microservicio DaviEventos

El proyecto sigue una arquitectura en capas bien definida:


```
src/
├── main/
│   ├── java/com/co/davivienda/ti/prueba/
│   │   ├── controllers/         # Controladores REST
│   │   ├── entities/            # Entidades de dominio
│   │   ├── exceptions/          # Excepciones personalizadas
│   │   ├── models/              # DTOs y objetos de transferencia
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── request/         # Objetos de solicitud
│   │   │   └── response/        # Objetos de respuesta
│   │   ├── repositories/        # Interfaces de acceso a datos
│   │   ├── security/            # Configuración de seguridad
│   │   │   ├── filters/         # Filtros de seguridad
│   │   │   └── services/        # Servicios de seguridad
│   │   ├── services/            # Lógica de negocio
│   │   └── PruebaApplication.java
│   └── resources/
│       ├── application.properties # Configuración
│       ├── banner.txt            # Banner personalizado
│       ├── logback-spring.xml    # Configuración de logs
│       └── import.sql            # Datos iniciales
```


## Características Principales

### 1. Gestión de Usuarios
- Registro de nuevos usuarios
- Inicio de sesión con JWT
- Validación de datos de usuario

### 2. Gestión de Categorías
- Listado de todas las categorías disponibles
- Creación de nuevas categorías

### 3. Gestión de Eventos
- Listado de eventos disponibles
- Creación, actualización y eliminación de eventos
- Filtrado de eventos por categoría

### 4. Sistema de Reservas
- Creación de reservas para eventos
- Visualización de reservas por usuario
- Actualización del estado de reservas (PENDIENTE, CONFIRMADA, CANCELADA)
- Eliminación de reservas
- Validación para prevenir reservas en eventos completos

## API Endpoints

### Autenticación
```
POST /api/users/register        # Registro de usuarios
POST /api/users/login           # Inicio de sesión
```
Categorías
```
GET  /api/categories            # Obtener todas las categorías
POST /api/categories            # Crear una nueva categoría
```
Eventos
```
GET  /api/events/all/{userId}          # Obtener todos los eventos
GET  /api/events/{userId}/{eventId}    # Obtener un evento específico
POST /api/events/{userId}              # Crear un nuevo evento
PUT  /api/events/{userId}/{eventId}    # Actualizar un evento
DELETE /api/events/{userId}/{eventId}  # Eliminar un evento
```
Reservas
```
GET  /api/reservations/{userId}                # Obtener todas las reservas de un usuario
GET  /api/reservations/{userId}/{reservationId} # Obtener una reserva específica
POST /api/reservations/{userId}                # Crear una nueva reserva
PUT  /api/reservations/{userId}/{reservationId} # Actualizar una reserva
DELETE /api/reservations/{userId}/{reservationId} # Eliminar una reserva
```
## Seguridad Implementada

El sistema implementa un mecanismo de seguridad basado en tokens JWT:

1. **Autenticación**: Los usuarios se autentican enviando sus credenciales.
2. **Token JWT**: El servidor genera un token JWT firmado.
3. **Autorización**: El token debe enviarse en el encabezado de las solicitudes posteriores.
4. **Validación**: Cada solicitud es validada verificando el token antes de procesarla.
5. **Contraseñas**: Las contraseñas se almacenan con encriptación mediante BCrypt.

## Validaciones del Sistema

- **Reservas**: Verifica que el evento tenga capacidad disponible antes de permitir una reserva.
- **Usuarios**: Validación de formato de correo electrónico y campos obligatorios.
- **Eventos**: Validación de capacidad, fechas y campos obligatorios.

## Base de Datos

El modelo de datos incluye las siguientes entidades principales:
- **User**: Almacena información de usuarios registrados.
- **Category**: Categorías para clasificar los eventos.
- **Event**: Información detallada de eventos.
- **Reservation**: Registros de reservas realizadas.
## Uso de la API

### Ejemplo de registro de usuario:
```
// POST /api/users/register
{
  "firstName": "Carlos",
  "lastName": "Rodríguez",
  "email": "carlos@example.com",
  "password": "password123",
  "confirmPassword": "password123"
}
```
Ejemplo de creación de reserva:
```
// POST /api/reservations/1
{
  "eventId": 3,
  "status": "PENDIENTE"
}
```
Ejemplo de actualización de reserva:
```
// PUT /api/reservations/1/5
{
  "status": "CONFIRMADA"
}
```

## Configuración del Entorno
El proyecto utiliza Maven para la gestión de dependencias. Las principales configuraciones se encuentran en:

* application.properties: Configuración de la base de datos, servidor y aplicación.
* logback-spring.xml: Configuración personalizada para logs.

## Perfiles de Ejecución
El sistema está configurado para soportar diferentes entornos de ejecución (desarrollo, pruebas, producción) mediante perfiles de Spring Boot.

## Documentación
El proyecto utiliza Javadoc para documentar sus componentes. La documentación completa se puede generar con:
```
mvn javadoc:javadoc
```
La documentación generada estará disponible en la carpeta docs.

## Conclusiones
Esta API proporciona una solución completa para la gestión de eventos y reservas, con un enfoque en la seguridad y la validación de datos. La arquitectura en capas facilita el mantenimiento y la escalabilidad, permitiendo añadir nuevas características con facilidad.

El sistema garantiza que los eventos no excedan su capacidad máxima y proporciona mecanismos seguros para la autenticación y autorización de usuarios.
______

Desarrollado por: José Daniel García Arias

Versión: 1.0.0

Fecha: Abril 2025
