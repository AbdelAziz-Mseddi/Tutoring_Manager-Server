# Entity Package

## Purpose
Maps Java classes to database tables using JPA annotations.

## Decorators Used Here

- `@Entity`: marks a class as a JPA entity.
- `@Table`: sets the database table name.
- `@Id`: marks the primary key field.
- `@GeneratedValue`: configures automatic ID generation.
- `@Column`: defines column constraints and metadata.
- `@ManyToOne`: declares a many-to-one relationship.
- `@JoinColumn`: maps the relationship to a foreign key column.
- `@CreationTimestamp`: fills the timestamp automatically on insert.
- `@PrePersist`: runs just before inserting a new entity.
- `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`: Lombok helpers that generate boilerplate methods and constructors.
- `@NotBlank`, `@NotNull`, `@Email`, `@DecimalMin`, `@Min`: validation annotations for entity input rules.

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
