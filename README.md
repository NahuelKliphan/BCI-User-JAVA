# User API

Esta aplicación expone una API RESTful para crear y listar usuarios.

Banco de datos en memoria H2 (No requiere scripts)

Ver documentación de swagger para probar

## Swagger

http://localhost:8080/documentation

## Requisitos
- Java 17
- Maven
 
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

## Comandos

- Compilar: mvn clean install
- Test: mvn test

## Requisitos opcionales

- Pruebas unitarias
- Swagger

