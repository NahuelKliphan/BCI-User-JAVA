# User API

Esta aplicación expone una API RESTful para crear y listar usuarios.

Banco de datos en memoria H2 (No requiere scripts)

Tres enpoints:

- Register:

POST: http://localhost:8080/user/register (sin segurizar)

Body de prueba:

{
  "name": "Juan Rodriguez",
  "email": "juan@rodriguez.org",
  "password": "123asd",
  "phones": [
    {
      "number": "1234567",
      "contrycode": "57"
    }
  ]
}

Con el token obtenido probar los demas endpoints poniendo en el header Authorization Bearer + token

GET: http://localhost:8080/user/login (Header Authorization + token)

Devuelve los datos del usuario logueado

GET: http://localhost:8080/user (Header Authorization + token)

Devuelve lista basica de usuario registrados

## Swagger

http://localhost:8080/swagger-ui/index.htm

## Requisitos
- Java 17
- Maven

## Comandos

- Compilar: mvn clean install
- Test: mvn test

## Requisitos opcionales

- Pruebas unitarias
- Swagger

 
## Configuración

Asegúrate de tener los siguientes parámetros en tu `application.properties`:
```properties

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

springdoc.swagger-ui.path=/documentation

regex.password=^(?=.*[a-zA-Z])(?=.*[0-9]).+$

