# Server Package Documentation

This package contains the complete backend API implementation for Tutoring Manager.

## Package Flow

1. Controllers receive HTTP requests.
2. Services execute business logic.
3. Repositories access the database.
4. Entities map Java objects to tables.
5. DTOs define request/response payloads.
6. Config and exception packages provide cross-cutting behavior.

## Class Catalog

### Entry Point
- `Main`: Starts the Spring Boot application and exposes OpenAPI metadata.

### Config
- `config/CorsConfig`: Enables CORS globally for all routes and HTTP methods.

### Controller
- `controller/StudentController`: Endpoints for student CRUD and paging.
- `controller/TutoringClassController`: Endpoints for tutoring class CRUD and paging.
- `controller/EnrollmentController`: Endpoints for enrollment CRUD, paging, and status patch.
- `controller/PaymentController`: Endpoints for payment CRUD and paging.
- `controller/TutoringSessionController`: Endpoints for tutoring session CRUD and paging.

### Service
- `service/StudentService`: Student business logic and mapping to response DTOs.
- `service/TutoringClassService`: Class business logic and mapping to response DTOs.
- `service/EnrollmentService`: Enrollment rules (defaults + status updates).
- `service/PaymentService`: Payment create/update/delete and mapping.
- `service/TutoringSessionService`: Session scheduling update/delete logic.

### Repository
- `repository/StudentRepository`: JPA persistence for students.
- `repository/TutoringClassRepository`: JPA persistence for classes.
- `repository/EnrollmentRepository`: JPA persistence for enrollments.
- `repository/PaymentRepository`: JPA persistence for payments.
- `repository/TutoringSessionRepository`: JPA persistence for sessions.

### Entity
- `entity/Student`: Maps to `student` table.
- `entity/TutoringClass`: Maps to `tutoring_class` table.
- `entity/Enrollment`: Maps to `enrollment` table and links student/class IDs.
- `entity/Payment`: Maps to `payment` table.
- `entity/TutoringSession`: Maps to `tutoring_session` table and links enrollment/payment IDs.

### DTO
- `dto/StudentRequest`: Incoming payload for creating/updating students.
- `dto/StudentResponse`: Outgoing payload for student data.
- `dto/TutoringClassRequest`: Incoming payload for creating/updating classes.
- `dto/TutoringClassResponse`: Outgoing payload for class data.
- `dto/EnrollmentRequest`: Incoming payload for creating/updating enrollments.
- `dto/EnrollmentResponse`: Outgoing payload for enrollment data.
- `dto/PaymentRequest`: Incoming payload for creating/updating payments.
- `dto/PaymentResponse`: Outgoing payload for payment data.
- `dto/TutoringSessionRequest`: Incoming payload for creating/updating sessions.
- `dto/TutoringSessionResponse`: Outgoing payload for session data.

### Exception
- `exception/ErrorResponse`: Standard error response body.
- `exception/GlobalExceptionHandler`: Centralized exception-to-HTTP response mapping.
