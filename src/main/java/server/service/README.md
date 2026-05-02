# Service Package

## Purpose
Implements business logic between controllers and repositories, including ownership enforcement.

## Decorators Used Here

- `@Service`: marks a class as a business-layer Spring bean.
- `@Autowired`: injects the repository or utility dependency used by the service.

## Classes
- `AuthService`
- `StudentService`
- `TutoringClassService`
- `EnrollmentService`
- `PaymentService`
- `TutoringSessionService`

## Class Details
- `AuthService`: Handles user registration (hashes password with BCrypt, checks for duplicate email, returns JWT) and login (validates credentials, returns JWT).
- `StudentService`: Handles student CRUD. All mutating operations and single-record lookups verify the student belongs to the authenticated user (`ForbiddenException` if not).
- `TutoringClassService`: Handles tutoring class CRUD with the same ownership pattern as `StudentService`.
- `EnrollmentService`: Handles enrollment CRUD and status updates. Ownership is verified by checking that the enrollment's student belongs to the authenticated user.
- `PaymentService`: Handles payment CRUD with the same ownership pattern as `StudentService`.
- `TutoringSessionService`: Handles session CRUD. Ownership is verified by traversing session → enrollment → student → user.

## Common Responsibilities Across Domain Services
- Filter paginated list queries by the authenticated `userId`.
- Throw `RuntimeException` (→ 404) when a record is not found.
- Throw `ForbiddenException` (→ 403) when a resource is owned by a different user.
- Convert entities into response DTOs.
- Enforce `@PrePersist` defaults for fields like `enrolledAt`, `status`, and `durationMin`.
