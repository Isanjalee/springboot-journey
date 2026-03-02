# 🌱 Spring Boot Learning Journey

---

## 🎯 Purpose of This Repository

This repository is my **personal learning space for Spring Boot**.

I am learning **step by step**, starting from zero and gradually building a **real-world backend application** using clean architecture, database integration, validation, and security.

---

### 🚀 My Goals

- Learn Spring Boot deeply
- Understand backend development concepts
- Build RESTful APIs
- Connect and work with databases
- Implement validation and error handling
- Add authentication and authorization
- Follow real-world backend practices
- Help beginners by sharing my learning journey

---

## 🗺️ What I Do Here

In this repository, I:

- Learn Spring Boot from **basics to advanced**
- Practice daily by adding small features
- Build APIs step by step
- Integrate databases
- Apply validation and clean error handling
- Implement authentication and authorization (JWT)
- Follow clean architecture principles
- Document each learning day clearly

---

## 🛠 Roadmap (Learning Path)

### 📘 Phase 1 — Basics
- Spring Boot setup
- REST APIs
- Controllers & Models
- Maven
- JSON handling

---

### 🧩 Phase 2 — Clean Architecture
- Service layer
- Repository layer
- DTOs
- Validation
- Separation of concerns

---

### 🗄 Phase 3 — Database
- JPA & Hibernate
- H2 database
- Entity mapping
- Persistence

---

### 🔐 Phase 4 — Security
- Spring Security
- Authentication
- JWT tokens
- Role-based access control

---

### 🌍 Phase 5 — Real World (Current / Ongoing)
- Pagination & sorting
- Logging
- File upload
- Email sending
- Performance basics

---

### ☁ Phase 6 — DevOps (Planned)
- Docker
- Docker Compose
- Profiles (dev / prod)
- Deployment

---

# ✅ What Is Done So Far

---

## 🟢 Day 01 — Environment & Setup
✔ Installed Java 17  
✔ Installed Maven  
✔ Fixed VS Code Java issues  
✔ Created Spring Boot project  
✔ Fixed port conflicts  
✔ Successfully ran the application  

---

## 🟢 Day 02 — REST CRUD
✔ Learned REST concepts  
✔ Built User model  
✔ Built User controller  
✔ Created CRUD endpoints  
✔ Tested APIs using Postman  
✔ Fixed package mismatch issues  

---

## 🟢 Day 03 — Service Layer (Clean Architecture)
✔ Introduced Service layer  
✔ Separated business logic from controller  
✔ Implemented `UserService`  
✔ Used `@Service` annotation  
✔ Implemented constructor-based dependency injection  
✔ Refactored controller to be thin  
✔ Applied clean architecture principles  

---

## 🟢 Day 04 — Database Integration (JPA)
✔ Introduced Spring Data JPA  
✔ Connected application to H2 database  
✔ Converted model to JPA Entity using `@Entity`  
✔ Used `@Id` and `@GeneratedValue`  
✔ Created Repository layer using `JpaRepository`  
✔ Replaced in-memory storage with database persistence  
✔ Verified data using H2 Console  

---

## 🟢 Day 05 — Validation & Error Handling
✔ Introduced DTOs for request and response  
✔ Implemented input validation using `@Valid`  
✔ Used validation annotations (`@NotBlank`, `@Email`, `@Size`)  
✔ Prevented invalid data from reaching service layer  
✔ Implemented global exception handling using `@RestControllerAdvice`  
✔ Returned clean HTTP 400 validation error responses  

---

## 🟢 Day 06 — Spring Security & JWT
✔ Introduced Spring Security  
✔ Implemented authentication using JWT  
✔ Created `/auth/login` endpoint  
✔ Generated JWT token after login  
✔ Secured APIs using JWT filter  
✔ Learned 401 vs 403 difference  

---

## 🟢 Day 07 — Role-Based Access Control (RBAC)
✔ Implemented ADMIN and USER roles  
✔ Restricted user creation to ADMIN only  
✔ Stored roles inside database and JWT  
✔ Enforced authorization using roles  
✔ Tested secured endpoints using Postman  

---

## 🟢 Day 08 — Security Hardening & Production Practices
✔ Implemented BCrypt password hashing  
✔ Prevented storing passwords in plain text  
✔ Updated login flow to verify hashed passwords  
✔ Added method-level security using `@PreAuthorize`  
✔ Enforced DTO-only API responses  
✔ Seeded initial ADMIN user on application startup  

---

## 🟢 Day 09 — Pagination, Sorting & Searching (Production API Design)
✔ Implemented pagination using Spring Data Pageable  
✔ Added sorting support  
✔ Added search by name and email  
✔ Learned why returning full dataset is bad for performance  
✔ Built scalable API design  

---
## 🟢 Day 10 — Logging & Production Observability
✔ Introduced application logging using SLF4J + Logback  
✔ Added logging inside Service layer for business events  
✔ Added logging inside Controller layer for API request tracking  
✔ Logged authentication and login attempts  
✔ Implemented exception logging inside Global Exception Handler  
✔ Configured log levels using application.properties  
✔ Learned importance of logging in real production systems  
✔ Understood difference between log levels: INFO, DEBUG, ERROR, WARN  

 ---
## 🟢 Day 11 — API Documentation & Developer Experience
✔ Integrated Swagger / OpenAPI using SpringDoc  
✔ Enabled Swagger UI for API testing and developer usage  
✔ Configured API documentation endpoint /swagger-ui.html  
✔ Learned how API documentation helps frontend teams and external consumers  
✔ Understood OpenAPI specification basics  
✔ Learned version compatibility importance (Spring Boot vs SpringDoc)  
✔ Fixed Swagger runtime compatibility issue (ControllerAdviceBean error)  
✔ Learned dependency management and version alignment in Maven  
✔ Verified API endpoints using Swagger UI instead of Postman

---
## 🟢 Day 12 — Production Configuration, Profiles & Environment Separation
✔ Introduced Spring Profiles (dev, prod, test)  
✔ Learned why environment separation is critical in real production systems  
✔ Created application-dev.properties for development environment  
✔ Prepared structure for application-prod.properties for production deployment  
✔ Configured profile-based database switching (H2 → PostgreSQL ready)  
✔ Learned how to run Spring Boot using profile activation (Maven + JVM args)  
✔ Fixed Maven profile command mistakes and understood correct syntax  
✔ Learned importance of environment-based secrets and configuration isolation  
✔ Verified profile activation using startup logs  
✔ Prepared project for real-world deployment environments

---
## 🟢 Day 13 — Professional Exception Handling & Error Architecture
✔ Removed generic `RuntimeException` usage  
✔ Implemented custom domain exceptions (`ResourceNotFoundException`, `DuplicateResourceException`)  
✔ Designed structured `ErrorResponse` DTO  
✔ Implemented global exception mapping using `@RestControllerAdvice`  
✔ Returned proper HTTP status codes (404, 409, 400, 500)  
✔ Ensured consistent error response format across entire API  
✔ Prevented stack trace leakage in production responses  
✔ Separated business exceptions from system exceptions  
✔ Applied clean separation of concerns (Controller → Service → Exception Handler)  
✔ Upgraded API to production-grade error handling architecture  

## 🟢 Day 14 — Unit Testing with JUnit & Mockito
✔ Introduced unit testing using JUnit 5  
✔ Implemented Service layer testing  
✔ Used Mockito for dependency mocking  
✔ Mocked `UserRepository` and `PasswordEncoder`  
✔ Tested business logic success scenarios  
✔ Tested exception scenarios using `assertThrows()`  
✔ Verified repository interactions using `verify()`  
✔ Learned difference between unit testing and integration testing  
✔ Understood importance of test isolation  
✔ Strengthened backend interview preparation  

---

# 📌 Status Tracker

| Day | Topic | Status |
|---|---|---|
| Day 01 | Setup & Run | ✅ Done |
| Day 02 | REST CRUD | ✅ Done |
| Day 03 | Service Layer | ✅ Done |
| Day 04 | Database (JPA) | ✅ Done |
| Day 05 | Validation & Error Handling | ✅ Done |
| Day 06 | Security (JWT) | ✅ Done |
| Day 07 | RBAC | ✅ Done |
| Day 08 | Security Hardening | ✅ Done |
| Day 09 | Pagination & Search | ✅ Done |
| Day 10 | Logging & Production Observability | ✅ Done |
| Day 11 | Swagger & API Documentation | ✅ Done |
| Day 12 | Production Readiness & Monitoring | ✅ Done |
| Day 13 | Professional Exception Handling with Custom Exceptions | ✅ Done |
| Day 14 | Unit Testing with JUnit & Mockito | ✅ Done |

---

# 📁 Project Structure

```

controller/   → REST API endpoints
service/      → Business logic
repository/   → Database access (JPA)
model/        → Entity classes
dto/          → Request / Response DTOs + validation
security/     → JWT utilities and filters
config/       → Security configuration
exception/    → Global exception handling

```

---

# ▶️ How To Run The Project

### Clone
```

git clone [https://github.com/Isanjalee/springboot-journey.git](https://github.com/Isanjalee/springboot-journey.git)
cd springboot-journey

```

### Run
```

mvn spring-boot:run

```

App runs on:
```

[http://localhost:8081](http://localhost:8081)

```

---

# 🔐 Authentication Flow

### Login
POST `/auth/login`

```

{
"email": "[admin@gmail.com](mailto:admin@gmail.com)",
"password": "admin123"
}

```

### Use Token
Header:
```

Authorization: Bearer <TOKEN>

```

---

# 🧪 Example API Testing

### Get Users (Pagination)
```

GET /users?page=0&size=3

```

### Sorting
```

GET /users?page=0&size=5&sort=name,asc

```

### Search
```

GET /users/search?name=isa&page=0&size=5

```

---

# 🧩 Why This Repository Is Useful

✔ Shows real beginner problems and fixes  
✔ Explains WHY things are done  
✔ Demonstrates real backend architecture  
✔ Good for beginners + interview prep  
✔ Real learning journey documentation  

---

## 📌 Note
This is not a perfect project — it is a **learning story**.  
Mistakes, fixes, and understanding are part of the journey.
