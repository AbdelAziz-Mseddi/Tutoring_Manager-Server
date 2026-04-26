# Entity Package

## Purpose
Maps Java classes to database tables using JPA annotations.

## Classes
- `Student`
- `TutoringClass`
- `Enrollment`
- `Payment`
- `TutoringSession`

## Class Details
- `Student`: Represents the `student` table with identity, personal info, and creation timestamp.
- `TutoringClass`: Represents `tutoring_class` with class metadata and hourly rate.
- `Enrollment`: Represents `enrollment` linking `student_id` and `class_id`, including status and enrollment date.
- `Payment`: Represents `payment` with amount, payment timestamp, and notes.
- `TutoringSession`: Represents `tutoring_session`, linking enrollment/payment with schedule and duration.

## Lifecycle Notes
- `Enrollment` has `@PrePersist` defaults for `enrolledAt` and `status`.
- `TutoringSession` has `@PrePersist` default for `durationMin`.
