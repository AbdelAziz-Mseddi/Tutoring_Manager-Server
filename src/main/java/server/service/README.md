# Service Package

## Purpose
Implements business logic between controllers and repositories.

## Decorators Used Here

- `@Service`: marks a class as a business-layer Spring bean.
- `@Autowired`: injects the repository dependency used by the service.

## Classes
- `StudentService`
- `TutoringClassService`
- `EnrollmentService`
- `PaymentService`
- `TutoringSessionService`

## Class Details
- `StudentService`: Handles student CRUD logic and entity-to-response mapping.
- `TutoringClassService`: Handles class CRUD logic and mapping.
- `EnrollmentService`: Handles enrollment CRUD, default values, and status updates.
- `PaymentService`: Handles payment CRUD and mapping.
- `TutoringSessionService`: Handles session CRUD and default duration behavior.

## Common Responsibilities Across Services
- Load paginated data from repositories.
- Throw runtime errors when records are not found.
- Convert entities into response DTOs.
- Enforce simple defaults before persistence.
