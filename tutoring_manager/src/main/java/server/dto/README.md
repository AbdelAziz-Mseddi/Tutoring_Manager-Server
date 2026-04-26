# DTO Package

## Purpose
Defines API payload contracts to keep external JSON models separate from persistence entities.

## Classes
- `StudentRequest`, `StudentResponse`
- `TutoringClassRequest`, `TutoringClassResponse`
- `EnrollmentRequest`, `EnrollmentResponse`
- `PaymentRequest`, `PaymentResponse`
- `TutoringSessionRequest`, `TutoringSessionResponse`

## Class Details
- `StudentRequest`: Input fields and validation for create/update student operations.
- `StudentResponse`: Output fields returned for student resources.
- `TutoringClassRequest`: Input fields and validation for class create/update operations.
- `TutoringClassResponse`: Output fields returned for class resources.
- `EnrollmentRequest`: Input for enrollment create/update (student, class, status/date).
- `EnrollmentResponse`: Output representation of enrollment data.
- `PaymentRequest`: Input for payment create/update with amount validation.
- `PaymentResponse`: Output representation of payment data.
- `TutoringSessionRequest`: Input for session scheduling and updates.
- `TutoringSessionResponse`: Output representation of session data.

## Why This Helps
Using DTOs prevents accidental exposure of internal entity structure and gives better control over API evolution.
