# Student Management System — Spring Boot CRUD REST API

A RESTful Student Management System built using **Java, Spring Boot, Spring Data JPA, Hibernate, and MySQL**. This project demonstrates how to build backend APIs, manage relational database records, implement layered architecture, and handle soft delete and hard delete operations.

The project is designed as a hands-on learning resource for beginners who want to understand backend development using the Spring ecosystem.

## Table of Contents

1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Technology Stack](#technology-stack)
4. [Project Architecture](#project-architecture)
5. [How the Application Works](#how-the-application-works)
6. [Prerequisites](#prerequisites)
7. [Installation and Setup](#installation-and-setup)
8. [Database Configuration](#database-configuration)
9. [Implementation Guide](#implementation-guide)
10. [REST API Documentation](#rest-api-documentation)
11. [Soft Delete vs. Hard Delete](#soft-delete-vs-hard-delete)
12. [Testing the APIs](#testing-the-apis)
13. [Common Errors and Solutions](#common-errors-and-solutions)
14. [Git and GitHub Workflow](#git-and-github-workflow)
15. [Learning Roadmap](#learning-roadmap)
16. [Future Improvements](#future-improvements)
17. [Author](#author)

## Project Overview

The Student Management System provides REST APIs to manage student records in a MySQL database.

Each student can have information such as:

- Student ID
- Name
- Email
- Roll number
- Age
- Subject
- Deleted status

The application follows a layered architecture to separate HTTP requests, business logic, database operations, and entity mapping.

### Project Objectives

- Understand Spring Boot application development.
- Learn REST API design and HTTP methods.
- Perform database operations using Spring Data JPA.
- Understand Hibernate ORM.
- Implement soft delete and hard delete.
- Learn dependency injection and constructor-based injection.
- Test APIs using Postman.
- Manage project versions using Git and GitHub.

## Features

- **Create Student:** Save new student records in the database.
- **Get Student by ID:** Retrieve an active student using the student ID.
- **Get All Active Students:** Retrieve students whose `deleted` status is `false`.
- **Update Student:** Update the details of an existing active student.
- **Soft Delete:** Mark a student as deleted without physically removing the database record.
- **Hard Delete:** Permanently remove a student record from the database.
- **Database Integration:** Use MySQL to store student information.
- **Repository Abstraction:** Use Spring Data JPA to simplify database operations.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot | Application framework |
| Spring Web | Build REST APIs |
| Spring Data JPA | Database access and repository abstraction |
| Hibernate | Object-relational mapping |
| MySQL | Relational database |
| Maven | Dependency management and build automation |
| Postman | API testing |
| IntelliJ IDEA | Development environment |
| Git and GitHub | Version control and source code hosting |

*Use the Java version supported by your project's Spring Boot configuration.*

## Project Architecture

The application uses a layered architecture.

```text
Client (Postman)
      |
      v
StudentController
      |
      v
StudentServices
      |
      v
StudentRepo
      |
      v
Spring Data JPA / Hibernate
      |
      v
MySQL Database
```

### 1. Controller Layer

**Package:** `Controller`

Responsibilities:

- Handle HTTP requests.
- Map endpoints using annotations such as `@GetMapping`, `@PostMapping`, `@PutMapping`, and `@DeleteMapping`.
- Accept request data and return responses.

### 2. Service Layer

**Package:** `Services`

Responsibilities:

- Implement business logic.
- Validate whether a student exists.
- Update student details.
- Apply soft-delete logic.
- Coordinate repository operations.

### 3. Repository Layer

**Package:** `Repository`

Responsibilities:

- Access student records.
- Save, retrieve, update, and delete database entities.
- Define derived query methods.

Example:

```java
public interface StudentRepo
        extends JpaRepository<Student, Long> {

    Optional<Student> findByIdAndDeletedIsFalse(Long id);

    List<Student> findByDeletedIsFalse();
}
```

### 4. Entity Layer

**Package:** `Entity`

The `Student` entity represents a student record in the database.

A typical entity contains fields such as `id`, `name`, `email`, `roll`, `age`, `subject`, and `deleted`.

The exact field names must match the entity implemented in the project.

## How the Application Works

When a client sends an HTTP request, the following process occurs:

1. The client sends a request to a REST endpoint.
2. The controller receives the request.
3. The service layer executes the business logic.
4. The repository communicates with the database through JPA and Hibernate.
5. The database returns the result.
6. The controller sends an HTTP response to the client.

This separation makes the application easier to understand, test, maintain, and extend.

## Prerequisites

Before running the project, install the following:

- Java Development Kit (JDK).
- Maven, or use the project's Maven Wrapper if available.
- MySQL Server.
- IntelliJ IDEA or another Java IDE.
- Postman or an equivalent REST API client.
- Git.

Verify your Java and Maven installations:

```bash
java --version
mvn --version
git --version
```

## Installation and Setup

### Step 1: Clone the Repository

Replace the URL below with the actual URL of your GitHub repository.

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git
```

Navigate to the project directory:

```bash
cd YOUR_REPOSITORY
```

### Step 2: Open the Project

Open the project in IntelliJ IDEA.

Wait for Maven to download and resolve the dependencies.

Check that the project contains:

```text
src/
  main/
    java/
      com/akn62/crud_basic/
        Controller/
        Entity/
        Repository/
        Services/
        CrudBasicApplication.java
    resources/
      application.properties
pom.xml
```

The folder structure above illustrates the expected organization; your actual structure may differ.

### Step 3: Create the MySQL Database

Open MySQL Workbench or your MySQL terminal and execute:

```sql
CREATE DATABASE student_management;
```

Verify that the database exists:

```sql
SHOW DATABASES;
```

### Step 4: Configure Database Connectivity

Open `src/main/resources/application.properties`.

Add or update the following configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Set the `DB_USERNAME` and `DB_PASSWORD` environment variables to your own MySQL credentials before running the application.

**Security note:** Never commit real database passwords, API keys, or other secrets to GitHub. For a production application, use a managed secrets solution and an appropriate database migration strategy.

### Step 5: Run the Application

Run the `CrudBasicApplication` main class from IntelliJ IDEA.

Alternatively, if Maven is configured correctly:

```bash
mvn spring-boot:run
```

The default application port is `8080`, unless changed in the project configuration.

When startup succeeds, the application should be ready to receive HTTP requests.

## Implementation Guide

This section explains how the major features are implemented.

### Step 1: Create the Student Entity

Create a `Student` class and annotate it with `@Entity`.

The entity maps Java objects to database records.

Example of the core soft-delete field:

```java
@Column(nullable = false)
private boolean deleted = false;
```

Use the appropriate ID mapping and the other student fields in your actual entity.

The `deleted` field tracks whether a record is active or soft-deleted.

### Step 2: Create the Repository

Extend `JpaRepository` to inherit standard database operations.

```java
public interface StudentRepo
        extends JpaRepository<Student, Long> {

    Optional<Student> findByIdAndDeletedIsFalse(Long id);

    List<Student> findByDeletedIsFalse();
}
```

Understanding the derived query methods:

- `findByIdAndDeletedIsFalse(id)` retrieves a student by ID only when the student is not deleted.
- `findByDeletedIsFalse()` retrieves all active students.
- `JpaRepository` provides built-in methods such as `save()`, `findById()`, `findAll()`, `existsById()`, and `deleteById()`.

**Important:** Spring Data JPA derives these queries from the method names. Property names must match the entity fields, and the `findBy` keyword must be correctly capitalized.

### Step 3: Implement the Service Layer

Use constructor-based dependency injection to provide the repository to the service.

```java
@Service
public class StudentServices {

    private final StudentRepo studentRepo;

    public StudentServices(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }
}
```

Constructor injection makes dependencies explicit and simplifies testing.

Implement service methods for creating, retrieving, updating, and deleting student records.

### Step 4: Create a Student

When a student is created, set `deleted` to `false` before saving the entity.

```java
public Student createStudent(Student student) {
    student.setDeleted(false);
    return studentRepo.save(student);
}
```

This ensures newly created students are active.

For a production API, validate incoming data and avoid accepting server-managed fields such as `id` and `deleted` directly from an unrestricted request body.

### Step 5: Retrieve an Active Student

```java
public Student getStudent(long id) {
    return studentRepo
            .findByIdAndDeletedIsFalse(id)
            .orElse(null);
}
```

This returns the active student when found. Otherwise, it returns `null`.

A more robust REST API should return HTTP `404 Not Found` when the student does not exist or is already soft-deleted.

### Step 6: Retrieve All Active Students

```java
public List<Student> getAllStudents() {
    return studentRepo.findByDeletedIsFalse();
}
```

The repository filters the results so that records marked `deleted = true` are excluded.

### Step 7: Update an Active Student

The update process should:

1. Retrieve the student by ID while filtering out deleted records.
2. Return an appropriate not-found response if no active record exists.
3. Update the permitted student fields.
4. Save the modified entity.

Using the existing entity is important because it preserves the record's identity and avoids accidentally creating a new record.

### Step 8: Implement Soft Delete

Soft delete updates a status field instead of removing the database record.

```java
public boolean deleteStudentSoft(long id) {
    Optional<Student> optionalStudent =
            studentRepo.findByIdAndDeletedIsFalse(id);

    if (optionalStudent.isEmpty()) {
        return false;
    }

    Student student = optionalStudent.get();
    student.setDeleted(true);
    studentRepo.save(student);

    return true;
}
```

After a successful soft delete:

- The database record remains present.
- The `deleted` field becomes `true`.
- Queries filtering on `deleted = false` no longer return that student.

### Step 9: Implement Hard Delete

Hard delete physically removes the record.

```java
public boolean deleteStudent(long id) {
    if (!studentRepo.existsById(id)) {
        return false;
    }

    studentRepo.deleteById(id);
    return true;
}
```

**Important:** The example above checks whether a record exists, including a previously soft-deleted record. If the application should hard-delete only active students, use `findByIdAndDeletedIsFalse()` instead.

Hard delete is irreversible through the normal application workflow unless a backup or another recovery mechanism exists.

### Step 10: Expose the Service Through a Controller

Create a controller using `@RestController` and define the required mappings.

For example:

```java
@RestController
@RequestMapping("/students")
public class StudentController {
    // Inject StudentServices and define endpoints here.
}
```

The exact mappings should match the endpoints in your actual controller. The following section provides a suggested REST API design.

## REST API Documentation

The following is a recommended endpoint design. Adjust the URLs and HTTP methods to match your implemented `StudentController`.

Base URL:

```text
http://localhost:8080
```

| Method | Suggested Endpoint | Purpose |
|---|---|---|
| POST | `/students` | Create a student |
| GET | `/students/{id}` | Get an active student by ID |
| GET | `/students` | Get all active students |
| PUT | `/students/{id}` | Update an active student |
| DELETE | `/students/{id}/soft` | Soft-delete a student |
| DELETE | `/students/{id}/hard` | Permanently delete a student |

### Sample Request: Create Student

```http
POST /students
Content-Type: application/json
```

```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "roll": 101,
  "age": 21,
  "subject": "Computer Science"
}
```

Expected behaviour: The application saves the student as an active record.

### Sample Request: Get All Active Students

```http
GET /students
```

Expected behaviour: Return a JSON array containing students whose `deleted` field is `false`.

### Sample Request: Update Student

```http
PUT /students/1
Content-Type: application/json
```

```json
{
  "name": "Rahul Sharma",
  "email": "rahul.updated@example.com",
  "roll": 101,
  "age": 22,
  "subject": "Java Programming"
}
```

Expected behaviour: Update the specified active student.

### Sample Request: Soft Delete

```http
DELETE /students/1/soft
```

Expected behaviour: Mark the student as deleted without removing the record from MySQL.

### Sample Request: Hard Delete

```http
DELETE /students/1/hard
```

Expected behaviour: Permanently remove the specified record from MySQL.

### Suggested HTTP Status Codes

| Status | Meaning |
|---|---|
| `200 OK` | Request succeeded |
| `201 Created` | Student created |
| `204 No Content` | Successful operation with no response body |
| `400 Bad Request` | Invalid request data |
| `404 Not Found` | Student not found |
| `500 Internal Server Error` | Unexpected server-side failure |

Choose the status code based on the actual controller implementation. A successful response does not automatically mean the controller already uses these codes.

## Soft Delete vs. Hard Delete

| Behaviour | Soft Delete | Hard Delete |
|---|---|---|
| Database record | Preserved | Removed |
| Deleted flag | Set to `true` | Not applicable after removal |
| Included in active-student queries | No | No |
| Recovery | Possible by restoring the flag if permitted | Requires a backup or other recovery mechanism |
| Common use | Retaining records and audit history | Permanent removal when appropriate |

### Example Lifecycle

Suppose the database contains this student:

| ID | Name | Deleted |
|---|---|---|
| 1 | Rahul | `false` |
| 2 | Amit | `false` |

After soft-deleting Rahul:

| ID | Name | Deleted |
|---|---|---|
| 1 | Rahul | `true` |
| 2 | Amit | `false` |

The active-student query returns only Amit.

If Rahul is subsequently hard-deleted, his database record is physically removed.

## Testing the APIs

Use Postman to test each operation.

Recommended testing sequence:

1. Start the Spring Boot application.
2. Create a student using `POST`.
3. Retrieve the student using `GET`.
4. Retrieve all active students.
5. Update the student using `PUT`.
6. Soft-delete the student.
7. Verify that the student no longer appears in the active-student list.
8. Verify that the student cannot be retrieved through the active-student endpoint.
9. Test hard delete using a separate test student.
10. Verify the hard-deleted record is no longer present in the database.

### Test Cases

| Test | Expected Result |
|---|---|
| Create a valid student | Student is saved |
| Retrieve an existing active student | Student is returned |
| Retrieve a nonexistent student | Not-found response |
| Update an active student | Student details are updated |
| Soft-delete an active student | `deleted` becomes `true` |
| List active students after soft delete | Deleted student is excluded |
| Soft-delete the same student again | Operation reports that no active student was found |
| Hard-delete an existing record | Record is removed |
| Hard-delete a nonexistent record | Operation reports that the record does not exist |

For hard-delete verification, check the database directly or use a repository query that can see all records.

## Common Errors and Solutions

### 1. `No property 'findbyDeleted' found for type 'Student'`

**Cause:** The repository method was declared with incorrect capitalization:

```java
findbyDeletedIsFalse()
```

**Solution:**

```java
findByDeletedIsFalse()
```

The method name must follow Spring Data JPA's derived query conventions.

### 2. `UnsatisfiedDependencyException`

**Cause:** A bean required by another bean could not be created. This can occur when a repository query method is invalid.

**Solution:** Read the deepest `Caused by` section in the stack trace first. Fix the underlying error instead of assuming the controller itself is incorrect.

### 3. MySQL Access Denied

**Cause:** The configured MySQL username or password is incorrect, or the account lacks the required privileges.

**Solution:** Verify the MySQL credentials and database permissions. Keep the credentials outside source control.

### 4. Database or Table Not Found

**Cause:** The database may not exist, or the entity-to-table configuration may be incorrect.

**Solution:** Verify the JDBC URL, database name, entity mappings, and schema-generation configuration.

### 5. Soft-Deleted Students Still Appear

**Cause:** A query may be using `findAll()` rather than filtering on the `deleted` field.

**Solution:** Use `findByDeletedIsFalse()` for the active-student list and ensure the service calls that method.

### 6. HTTP 500 Internal Server Error

**Cause:** An unhandled server-side exception occurred.

**Solution:** Inspect the application logs and stack trace, identify the underlying exception, and handle expected errors appropriately.

## Git and GitHub Workflow

Use Git to save incremental versions of the project.

### Step 1: Check Changed Files

```bash
git status
```

### Step 2: Stage the Changes

```bash
git add .
```

### Step 3: Commit the Work

```bash
git commit -m "Implement soft and hard delete for students"
```

### Step 4: Push to GitHub

```bash
git push
```

If the branch or remote has not been configured, configure it first. For example, if your current branch is `main` and the `origin` remote already exists:

```bash
git push -u origin main
```

After pushing, verify that the latest commit and changed files appear in the correct GitHub repository.

## Learning Roadmap

Follow this order to learn the project effectively.

### Beginner Level

- Learn Java classes, objects, methods, constructors, and interfaces.
- Understand OOP concepts and exception handling.
- Learn basic SQL and relational databases.
- Understand HTTP methods and JSON.
- Learn the purpose of Maven dependencies.

### Intermediate Level

- Create a Spring Boot application.
- Learn dependency injection and Spring annotations.
- Understand controllers, services, repositories, and entities.
- Implement CRUD operations using Spring Data JPA.
- Configure MySQL and understand Hibernate.
- Learn how derived query methods work.

### Advanced Level

- Implement input validation using Jakarta Validation.
- Handle errors with `@ControllerAdvice` and `@ExceptionHandler`.
- Use DTOs to separate API requests from database entities.
- Add pagination and sorting.
- Write unit and integration tests.
- Implement authentication and authorization with Spring Security.
- Add database migrations using Flyway or Liquibase.
- Learn logging, API documentation with OpenAPI, and deployment.

## Future Improvements

Potential enhancements for the project include:

- DTOs and request validation.
- Standardized error responses.
- Pagination and sorting.
- Search students by name, email, or roll number.
- Restore soft-deleted students.
- Track deletion timestamps and audit information.
- Global soft-delete filtering where appropriate.
- Unit tests and integration tests.
- Spring Security and role-based access control.
- Swagger/OpenAPI documentation.
- Docker-based application and database setup.
- Deployment to a cloud platform.

**Important:** Soft delete is a business rule, not a security boundary. Ensure that every relevant query and operation respects the deleted status. For a production system, also consider transaction management, concurrency, database constraints, and appropriate authorization.

## Author

**Abhishek Nishad**

BCA Student | Aspiring Java Backend Developer

- GitHub: [AbhishekNishad02](https://github.com/AbhishekNishad02)
- LinkedIn: [Abhishek Nishad](https://www.linkedin.com/in/abhishek-nishad-7b6158353/)

## License

This project is intended for educational and portfolio purposes. Add a `LICENSE` file if you want to distribute the project under a specific open-source license.

---

*Built to learn Java backend development, REST APIs, Spring Data JPA, Hibernate, MySQL, and reliable CRUD design.*
