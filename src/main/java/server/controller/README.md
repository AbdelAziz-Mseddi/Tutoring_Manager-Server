# Controller Package

## Purpose
Defines REST endpoints. Controllers parse HTTP input, delegate to services, and return HTTP responses.

## Decorators Used Here

- `@RestController`: makes the class a JSON-producing HTTP controller.
- `@RequestMapping`: defines the base path for the controller.
- `@Autowired`: injects the service dependency.
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@PatchMapping`, `@DeleteMapping`: map methods to HTTP verbs.
- `@RequestParam`: reads query parameters such as pagination inputs.
- `@PathVariable`: reads values from the URL path.
- `@RequestBody`: deserializes JSON into a DTO.
- `@Valid`: validates the incoming DTO before the method runs.

## Classes
- `AuthController`
- `StudentController`
- `TutoringClassController`
- `EnrollmentController`
- `PaymentController`
- `TutoringSessionController`

## Class Details
- `AuthController`: Handles `/auth/register` (POST, public) and `/auth/login` (POST, public). Both return a JWT token and the user's ID. No authentication required.
- `StudentController`: Manages `/students` endpoints for list, get by ID, create, update, delete. All routes are scoped to the authenticated user.
- `TutoringClassController`: Manages `/tutoring-classes` endpoints for list, get by ID, create, update, delete. All routes are scoped to the authenticated user.
- `EnrollmentController`: Manages `/enrollments` endpoints for list, get by ID, create, update, delete, and `PATCH /{id}/status`. Scoped to enrollments whose student belongs to the authenticated user.
- `PaymentController`: Manages `/payments` endpoints for list, get by ID, create, update, delete. All routes are scoped to the authenticated user.
- `TutoringSessionController`: Manages `/tutoring-sessions` endpoints for list, get by ID, create, update, delete. Scoped to sessions linked to the authenticated user's enrollments.

## Notes
- All controllers (except `AuthController`) call `SecurityContextHolder.getContext().getAuthentication().getPrincipal()` to get the authenticated `userId` and pass it to the service.
- All list endpoints support pagination with `page` and `size` query parameters. `userId` is never a query parameter — it comes from the token.
- Validation is applied to request bodies using `@Valid`.
