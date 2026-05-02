# DTO Package

## Purpose
Defines API payload contracts to keep external JSON models separate from persistence entities.

## Decorators Used Here

- `@Data`: generates getters, setters, `toString`, `equals`, and `hashCode`.
- `@NoArgsConstructor`: creates a no-argument constructor.
- `@AllArgsConstructor`: creates a constructor with all fields.
- `@NotBlank`, `@NotNull`, `@Email`, `@DecimalMin`, `@Min`: validate incoming request data.

## Classes
- `RegisterRequest`, `LoginRequest`, `AuthResponse`
- `StudentRequest`, `StudentResponse`
- `TutoringClassRequest`, `TutoringClassResponse`
- `EnrollmentRequest`, `EnrollmentResponse`
- `PaymentRequest`, `PaymentResponse`
- `TutoringSessionRequest`, `TutoringSessionResponse`

## Class Details

**Auth**
- `RegisterRequest`: firstName, lastName, email, password. Used by `POST /auth/register`.
- `LoginRequest`: email, password. Used by `POST /auth/login`.
- `AuthResponse`: token (JWT string), userId. Returned by both auth endpoints.

**Domain resources**
- `StudentRequest`: firstName, lastName, phone (optional), email (optional). No `userId` — derived from the JWT token.
- `StudentResponse`: id, userId, firstName, lastName, phone, email, createdAt.
- `TutoringClassRequest`: name, subject (optional), hourlyRate. No `userId` — derived from the JWT token.
- `TutoringClassResponse`: id, userId, name, subject, hourlyRate, createdAt.
- `EnrollmentRequest`: studentId, classId, enrolledAt (optional), status (optional).
- `EnrollmentResponse`: id, studentId, classId, enrolledAt, status.
- `PaymentRequest`: totalAmount, notes (optional). No `userId` — derived from the JWT token.
- `PaymentResponse`: id, userId, totalAmount, paidAt, notes.
- `TutoringSessionRequest`: enrollmentId, paymentId (optional), scheduledAt, durationMin (optional, defaults to 60), notes (optional).
- `TutoringSessionResponse`: id, enrollmentId, paymentId, scheduledAt, durationMin, notes, createdAt.

## Why This Helps
Using DTOs prevents accidental exposure of internal entity structure (e.g., passwordHash) and gives better control over API evolution.
