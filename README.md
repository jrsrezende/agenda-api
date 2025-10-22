# 📅 Agenda API

Application developed in Java with Spring Boot for managing tasks and categories.
It allows creating, listing, updating, and deleting tasks, with support for JWT authentication, Swagger documentation, and Docker integration.
The project was created with the goal of providing a simple and well-structured RESTful API, applying best practices.

## Features
- CRUD for Tasks
- CRUD for Categories
-  Authentication and authorization with JWT
- Documentation with Swagger/OpenAPI
- CORS configuration
- Global exception handling
- Modular structure (Controller, Service, Repository)
- Docker Support

## Tecnologies
- Java 17
- Spring Boot 3
  - Spring Web
  - Spring Data JPA
  - Spring Validation
- Swagger / SpringDoc OpenAPI
- JWT (JSON Web Token)
- Maven
- Docker

## How to Run
1. Clone the repository: https://github.com/jrsrezende/agenda-api
2. Run the cointainer:
```bash
javac -d out/production/chess-system -cp src src/application/Program.java
```
