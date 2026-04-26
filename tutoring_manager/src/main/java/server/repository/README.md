# Repository Package

## Purpose
Provides persistence access via Spring Data JPA interfaces.

## Decorators Used Here

- `@Repository`: marks the interface as a Spring data access bean.

## Reused Behavior

All repositories extend `JpaRepository`, which already provides the common CRUD methods (`findAll`, `findById`, `save`, `deleteById`).

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
