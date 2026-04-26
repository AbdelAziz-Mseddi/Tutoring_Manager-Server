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

## Class Details
- `ErrorResponse`: Standard JSON error body containing message, status code, and timestamp.
- `GlobalExceptionHandler`: Captures validation errors, runtime not-found style errors, and unexpected exceptions, then maps them to HTTP responses.

## Why This Helps
A single error format makes frontend and mobile error handling consistent and predictable.
