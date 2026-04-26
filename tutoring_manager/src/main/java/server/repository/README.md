# Repository Package

## Purpose
Provides persistence access via Spring Data JPA interfaces.

## Classes
- `StudentRepository`
- `TutoringClassRepository`
- `EnrollmentRepository`
- `PaymentRepository`
- `TutoringSessionRepository`

## Class Details
- `StudentRepository`: CRUD and query support for `Student` entities.
- `TutoringClassRepository`: CRUD and query support for `TutoringClass` entities.
- `EnrollmentRepository`: CRUD and query support for `Enrollment` entities.
- `PaymentRepository`: CRUD and query support for `Payment` entities.
- `TutoringSessionRepository`: CRUD and query support for `TutoringSession` entities.

## Notes
These interfaces inherit standard methods (`findAll`, `findById`, `save`, `deleteById`) from `JpaRepository`.
