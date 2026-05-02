# Exception Package

## Purpose
Centralizes API error structure and exception handling behavior.

## Decorators Used Here

- `@Data`: generates getters, setters, and utility methods for the error payload.
- `@AllArgsConstructor`: creates the constructor used to build the error payload.
- `@RestControllerAdvice`: applies global exception handling to all controllers and returns JSON.
- `@ExceptionHandler`: maps a specific exception type to a response handler method.

## Classes
- `ErrorResponse`
- `GlobalExceptionHandler`
- `UnauthorizedException`
- `ForbiddenException`
- `ConflictException`

## Class Details
- `ErrorResponse`: Standard JSON error body containing `message`, `status` (HTTP code), and `timestamp`.
- `GlobalExceptionHandler`: Captures all exception types and maps them to HTTP responses:
  - `UnauthorizedException` → `401 Unauthorized`
  - `ForbiddenException` → `403 Forbidden`
  - `ConflictException` → `409 Conflict`
  - `MethodArgumentNotValidException` → `400 Bad Request` (Bean Validation failures)
  - `RuntimeException` → `404 Not Found` (record-not-found style errors)
  - `Exception` → `500 Internal Server Error` (unexpected errors)
- `UnauthorizedException`: Thrown when credentials are invalid (wrong password, unknown email).
- `ForbiddenException`: Thrown when the authenticated user tries to access a resource they do not own.
- `ConflictException`: Thrown when a uniqueness constraint is violated (e.g., duplicate email on registration).

## Why This Helps
A single error format makes frontend and mobile error handling consistent and predictable. Typed exceptions keep business logic readable — services throw meaningful exceptions rather than setting HTTP status codes directly.
