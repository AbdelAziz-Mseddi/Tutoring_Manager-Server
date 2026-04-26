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
