# Spring Boot Learning Journey

This repository is a step-by-step Spring Boot backend learning project. It starts with simple REST APIs and grows into a more production-minded application with validation, security, persistence, migrations, and multiple testing layers.

## Current Features

- REST APIs with layered architecture
- DTO-based request and response handling
- validation with Bean Validation
- global exception handling
- JPA with H2 for development and PostgreSQL-ready profile config
- JWT authentication and role-based authorization
- pagination, sorting, and search
- logging and actuator endpoints
- Swagger / OpenAPI
- Flyway database migrations
- unit tests, repository slice tests, controller slice tests, integration tests, and JaCoCo coverage reporting

## API Summary

- `POST /auth/login`
- `GET /`
- `GET /hello`
- `POST /users`
- `GET /users`
- `GET /users/{id}`
- `PUT /users/{id}`
- `DELETE /users/{id}`
- `GET /users/search?name=...`
- `GET /users/search?email=...`

## Tech Stack

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- H2
- PostgreSQL driver
- Flyway
- SpringDoc OpenAPI
- JUnit 5
- Mockito
- MockMvc

## Project Structure

```text
src/main/java/com/isanjalee/demo/springbootdemo
|- config
|- controller
|- dto
|- exception
|- model
|- repository
|- security
`- service
```

## Profiles

- `application.properties`: shared defaults
- `application-dev.properties`: local development
- `application-prod.properties`: production-style PostgreSQL setup
- `application-docker.properties`: Docker / PostgreSQL setup

The default active profile is `dev`.

## Database Migrations

Flyway manages the schema from:

```text
src/main/resources/db/migration
```

The initial migration creates the `users` table.

## How To Run

### Local

```bash
mvn spring-boot:run
```

Application URL:

```text
http://localhost:8081
```

### Swagger UI

```text
http://localhost:8081/swagger-ui.html
```

### H2 Console

```text
http://localhost:8081/h2-console
```

## Authentication Flow

Login request:

```json
{
  "email": "admin@gmail.com",
  "password": "admin123"
}
```

Use the returned JWT in the header:

```text
Authorization: Bearer <TOKEN>
```

## Testing

Run the full suite:

```bash
mvn test
```

Run tests and generate coverage report:

```bash
mvn verify
```

Run one class:

```bash
mvn -Dtest=UserServiceTest test
```

## Test Layers In This Repo

- unit tests for service logic
- `@DataJpaTest` for repository behavior
- `@WebMvcTest` for controller slices
- `@SpringBootTest` integration tests for full request flow
- security-focused tests for unauthorized, forbidden, and invalid-token scenarios

JaCoCo HTML report:

```text
target/site/jacoco/index.html
```

## Learning Progress Covered

- Spring Boot setup
- CRUD APIs
- service layer and clean separation
- JPA persistence
- DTO validation
- exception handling
- JWT security
- RBAC with `@PreAuthorize`
- pagination and search
- logging and observability basics
- API documentation
- profile-based configuration
- database migrations
- unit and integration testing

## Notes

- This is a learning repository, not a final enterprise system.
- The goal is clean progression from beginner to strong backend fundamentals.
- The next recommended step after this repo is a larger second project with entity relationships, transactions, Flyway-first design, and Testcontainers.
