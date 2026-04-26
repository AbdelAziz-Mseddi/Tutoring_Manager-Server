# Controller Package

## Purpose
Defines REST endpoints. Controllers parse HTTP input, delegate to services, and return HTTP responses.

## Classes
- `StudentController`
- `TutoringClassController`
- `EnrollmentController`
- `PaymentController`
- `TutoringSessionController`

## Class Details
- `StudentController`: Manages `/students` endpoints for list, get by ID, create, update, delete.
- `TutoringClassController`: Manages `/tutoring-classes` endpoints for list, get by ID, create, update, delete.
- `EnrollmentController`: Manages `/enrollments` endpoints for list, get by ID, create, update, delete, and `PATCH /{id}/status`.
- `PaymentController`: Manages `/payments` endpoints for list, get by ID, create, update, delete.
- `TutoringSessionController`: Manages `/tutoring-sessions` endpoints for list, get by ID, create, update, delete.

## Notes
- All list endpoints support pagination with `page` and `size` query parameters.
- Validation is applied to request bodies using `@Valid`.
