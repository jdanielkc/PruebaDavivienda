# Caso de negocio (KATA)
Se solicita desarrollar una plataforma de eventos sencilla en donde en la página principal se puedan ver todos los eventos disponibles que pueda tener una persona (conciertos, citas médicas, restaurantes, cine y todos los eventos que usted desee) que permita a los usuarios ver eventos disponibles, registrarse y reservar espacios.

- La persona debe estar registrada: ingresar e iniciar sesión con el método de autenticación que usted desee.
- Debe reservar y mostrar los eventos que tiene reservados. 
- Los eventos deben tener un límite de personas; si supera el número de personas ya no les debe permitir realizar la inscripción y arrojar un mensaje. 
- Pueden utilizar IA y en la sustentación deben explicar cómo lo utilizaron.
- El orden de la sustentación será asignado de acuerdo al orden en el que envíen el link y la hora de la sustentación será confirmada por correo.


# Diseño y arquitectura propuesta
![DiagramaEntidadRelacion](https://github.com/user-attachments/assets/ccb2b8e8-6bb5-4cf3-967a-11e6cf6ed87a)

# __Sistema de Gestión de Reservas y Eventos__
## Descripción del Proyecto Backend
Este proyecto implementa una API RESTful para una plataforma de gestión de eventos que permite a los usuarios ver eventos disponibles, registrarse en la plataforma, iniciar sesión y realizar reservas para diferentes tipos de eventos (conciertos, citas médicas, restaurantes, cine, etc.). El sistema valida la disponibilidad de cupos y mantiene un registro de todas las reservaciones realizadas por los usuarios.

Tecnologías Implementadas
* __Java 17__: Lenguaje base para el desarrollo del backend
* __Spring Boot 3.4.4__: Framework para el desarrollo de aplicaciones Java
* __Spring Security__: Para la autenticación y autorización
* __AWS RDS (Postgresql)__: Se usa AWS para consumir la base de datos
* __Spring Data JPA__: Para la persistencia de datos
* __JWT (JSON Web Tokens)__: Implementación de autenticación stateless
* __PostgreSQL__: Sistema de gestión de base de datos relacional
* __Maven__: Gestión de dependencias y construcción del proyecto
* __Lombok__: Reducción de código boilerplate
* __Logback__: Framework de logging con soporte para formatos personalizados
* __JUnit & Spring Test__: Framework de pruebas unitarias e integración

## Patrones de Diseño Implementados
* DTO (Data Transfer Object): Para transferir datos entre capas.
* Repository Pattern: Para abstraer y encapsular el acceso a datos.
* Service Layer: Para encapsular la lógica de negocio.
* Builder Pattern: Utilizado con Lombok para la creación de objetos.
* Dependency Injection: Fundamental en la arquitectura Spring.

## Estructura del microservicio DaviEventos
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
1. Gestión de Usuarios
    - Registro de nuevos usuarios
    - Inicio de sesión con JWT
    - Validación de datos de usuario
2. Gestión de Categorías
    - Listado de todas las categorías disponibles
    - Creación de nuevas categorías
3. Gestión de Eventos
    - Listado de eventos disponibles
    - Creación, actualización y eliminación de eventos
    - Filtrado de eventos por categoría
4. Sistema de Reservas
    - Creación de reservas para eventos
    - Visualización de reservas por usuario
    - Actualización del estado de reservas (PENDIENTE, CONFIRMADA, CANCELADA)
    - Eliminación de reservas
    - Validación para prevenir reservas en eventos completos
## API Endpoints
Autenticación
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

1. __Autenticación__: Los usuarios se autentican enviando sus credenciales.
2. __Token JWT__: El servidor genera un token JWT firmado.
3. __Autorización__: El token debe enviarse en el encabezado de las solicitudes posteriores.
4. __Validación__: Cada solicitud es validada verificando el token antes de procesarla.
5. __Contraseñas__: Las contraseñas se almacenan con encriptación mediante BCrypt.
## Validaciones del Sistema
* __Reservas__: Verifica que el evento tenga capacidad disponible antes de permitir una reserva.
* __Usuarios__: Validación de formato de correo electrónico y campos obligatorios.
* __Eventos__: Validación de capacidad, fechas y campos obligatorios.


## Base de Datos
El modelo de datos incluye las siguientes entidades principales:

* __User__: Almacena información de usuarios registrados.
* __Category__: Categorías para clasificar los eventos.
* __Event__: Información detallada de eventos.
* __Reservation__: Registros de reservas realizadas.
## Uso de la API
Ejemplo de registro de usuario:
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
