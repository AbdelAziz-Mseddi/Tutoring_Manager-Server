# Exception Package

## Purpose
Centralizes API error structure and exception handling behavior.

## Classes
- `ErrorResponse`
- `GlobalExceptionHandler`

## Class Details
- `ErrorResponse`: Standard JSON error body containing message, status code, and timestamp.
- `GlobalExceptionHandler`: Captures validation errors, runtime not-found style errors, and unexpected exceptions, then maps them to HTTP responses.

## Why This Helps
A single error format makes frontend and mobile error handling consistent and predictable.
