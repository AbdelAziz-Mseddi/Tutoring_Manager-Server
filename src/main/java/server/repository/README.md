# Repository Package

## Purpose
Provides persistence access via Spring Data JPA interfaces.

## Decorators Used Here

- `@Repository`: marks the interface as a Spring data access bean.

## Reused Behavior

All repositories extend `JpaRepository`, which already provides the common CRUD methods (`findAll`, `findById`, `save`, `deleteById`, `existsById`).

## Classes
- `UserRepository`
- `StudentRepository`
- `TutoringClassRepository`
- `EnrollmentRepository`
- `PaymentRepository`
- `TutoringSessionRepository`

## Class Details
- `UserRepository`: CRUD for `User` entities. Adds `findByEmail(String email)` used during login and duplicate-email checks at registration.
- `StudentRepository`: CRUD for `Student` entities. Adds `findAllByUser_Id(Integer userId, Pageable)` to page-filter students by owner.
- `TutoringClassRepository`: CRUD for `TutoringClass` entities. Adds `findAllByUser_Id(Integer userId, Pageable)` to page-filter classes by owner.
- `EnrollmentRepository`: CRUD for `Enrollment` entities. Adds `findAllByStudent_User_Id(Integer userId, Pageable)` to page-filter enrollments by the owning user via the student relation.
- `PaymentRepository`: CRUD for `Payment` entities. Adds `findAllByUser_Id(Integer userId, Pageable)` to page-filter payments by owner.
- `TutoringSessionRepository`: CRUD for `TutoringSession` entities. Adds `findAllByEnrollment_Student_User_Id(Integer userId, Pageable)` to page-filter sessions by the owning user via the enrollment → student chain.
