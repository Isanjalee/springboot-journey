# 🌱 Spring Boot Learning Journey

## 🎯 Purpose of This Repository

This repository is my **personal learning space for Spring Boot**.

I am learning **step by step**, starting from zero and gradually building a **real-world backend application** using clean architecture, database integration, validation, and security.

### My goals:

* Learn Spring Boot deeply
* Understand backend development concepts
* Build RESTful APIs
* Connect and work with databases
* Implement validation and error handling
* Add authentication and authorization
* Follow real-world backend practices
* Help beginners by sharing my learning journey

---

## 🗺️ What I Do Here

In this repository, I:

* Learn Spring Boot from **basics to advanced**
* Practice daily by adding small features
* Build APIs step by step
* Integrate databases
* Apply validation and clean error handling
* Implement authentication and authorization (JWT)
* Follow clean architecture principles
* Document each learning day clearly

---

## 🛠 Roadmap (Learning Path)

### Phase 1 — Basics

* Spring Boot setup
* REST APIs
* Controllers & Models
* Maven
* JSON handling

### Phase 2 — Clean Architecture

* Service layer
* Repository layer
* DTOs
* Validation
* Separation of concerns

### Phase 3 — Database

* JPA & Hibernate
* H2 database
* Entity mapping
* Persistence

### Phase 4 — Security

* Spring Security
* Authentication
* JWT tokens
* Role-based access control

### Phase 5 — Real World (Planned)

* Pagination & sorting
* Logging
* File upload
* Email sending
* Performance basics

### Phase 6 — DevOps (Planned)

* Docker
* Docker Compose
* Profiles (dev / prod)
* Deployment

---

## ✅ What Is Done So Far

### 🟢 Day 01 — Environment & Setup

✔ Installed Java 17
✔ Installed Maven
✔ Fixed VS Code Java issues
✔ Created Spring Boot project
✔ Fixed port conflicts
✔ Successfully ran the application

---

### 🟢 Day 02 — REST CRUD

✔ Learned REST concepts
✔ Built User model
✔ Built User controller
✔ Created CRUD endpoints
✔ Tested APIs using Postman
✔ Fixed package mismatch issues

---

### 🟢 Day 03 — Service Layer (Clean Architecture)

✔ Introduced Service layer
✔ Separated business logic from controller
✔ Implemented `UserService`
✔ Used `@Service` annotation
✔ Implemented constructor-based dependency injection
✔ Refactored controller to be thin
✔ Applied clean architecture principles

---

### 🟢 Day 04 — Database Integration (JPA)

✔ Introduced Spring Data JPA
✔ Connected application to H2 database
✔ Converted model to JPA Entity using `@Entity`
✔ Used `@Id` and `@GeneratedValue` for primary key
✔ Created Repository layer using `JpaRepository`
✔ Replaced in-memory storage with real database persistence
✔ Verified data using H2 Console
✔ Controller remained unchanged (proved clean architecture works)

---

### 🟢 Day 05 — Validation & Error Handling

✔ Introduced DTOs for request and response
✔ Implemented input validation using `@Valid`
✔ Used validation annotations (`@NotBlank`, `@Email`, `@Size`)
✔ Prevented invalid data from reaching service layer
✔ Implemented global exception handling using `@RestControllerAdvice`
✔ Returned clean and meaningful HTTP 400 error responses

---

### 🟢 Day 06 — Spring Security & JWT

✔ Introduced Spring Security
✔ Implemented authentication using JWT
✔ Created `/auth/login` endpoint
✔ Generated JWT token on successful login
✔ Secured APIs using JWT filter
✔ Understood authentication vs authorization
✔ Learned difference between 401 and 403 errors

---

### 🟢 Day 07 — Role-Based Access Control (RBAC)

✔ Implemented ADMIN and USER roles
✔ Restricted user creation to ADMIN only
✔ Stored roles in database and JWT token
✔ Enforced authorization rules using roles
✔ Tested secured endpoints using Postman Bearer tokens

---

### 🟢 Day 08 — Security Hardening & Production Practices

✔ Implemented BCrypt password hashing
✔ Ensured passwords are never stored in plain text
✔ Updated login flow to validate hashed passwords
✔ Added method-level security using `@PreAuthorize`
✔ Enforced DTO-only API responses (no entity exposure)
✔ Seeded initial ADMIN user on application startup
✔ Finalized clean, production-ready backend structure

---

## 📌 Status Tracker

| Day    | Topic                       | Status |
| ------ | --------------------------- | ------ |
| Day 01 | Setup & Run                 | ✅ Done |
| Day 02 | REST CRUD                   | ✅ Done |
| Day 03 | Service Layer               | ✅ Done |
| Day 04 | Database (JPA)              | ✅ Done |
| Day 05 | Validation & Error Handling | ✅ Done |
| Day 06 | Security (JWT)              | ✅ Done |
| Day 07 | Role-Based Access Control   | ✅ Done |
| Day 08 | Security Hardening          | ✅ Done |

---

## 📁 Project Structure

```text
controller/   → REST API endpoints
service/      → Business logic
repository/   → Database access (JPA)
model/        → Entity classes
dto/          → Request/Response objects + validation
security/     → JWT utilities and filters
config/       → Security configuration & beans
exception/    → Global exception handling
```

---

## 🧩 Why This Repository Is Useful

* Shows **real beginner problems and solutions**
* Explains **why things are done**, not just how
* Demonstrates **clean backend architecture**
* Useful for beginners learning Spring Boot step by step
* Documents a **real learning journey**, not a perfect tutorial

---

📌 **Note:**
This is not a perfect project — it is a **learning story**.
Mistakes, fixes, and understanding are all part of the journey.

